package com.rabbiter.hospital.controller;

import com.rabbiter.hospital.pojo.Admin;
import com.rabbiter.hospital.pojo.Doctor;
import com.rabbiter.hospital.service.AdminService;
import com.rabbiter.hospital.service.DoctorService;
import com.rabbiter.hospital.service.OrderService;
import com.rabbiter.hospital.service.PatientService;
import com.rabbiter.hospital.utils.CaptchaRedisUtil;
import com.rabbiter.hospital.utils.CaptchaUtil;
import com.rabbiter.hospital.utils.JwtUtil;
import com.rabbiter.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin")  //@GetMapping("/admin") 规定请求方法只能用GET
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CaptchaRedisUtil captchaRedisUtil;

    // AdminController添加验证码接口
    @RequestMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 生成验证码
        String code = CaptchaUtil.generateCode();
        // 用sessionId作为key存储到Redis
        String sessionId = request.getSession().getId();
        captchaRedisUtil.setCaptcha("admin:captcha:" + sessionId, code);

        // 输出验证码图片
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        CaptchaUtil.generateImage(code, response.getOutputStream());
    }

    /**
     * 登录数据验证
     */
    @PostMapping("/login")
    public ResponseData login(@RequestParam("aId") int aId,
                              @RequestParam("aPassword") String aPassword,
                              @RequestParam("captcha") String captcha,
                              HttpServletRequest request) {
        // 验证验证码
        String sessionId = request.getSession().getId();
        String storedCode = captchaRedisUtil.getCaptcha("admin:captcha:" + sessionId);

        if (storedCode == null || !storedCode.equalsIgnoreCase(captcha)) {
            return ResponseData.fail("验证码错误或已过期");
        }

        // 验证成功后删除验证码
        captchaRedisUtil.deleteCaptcha("admin:captcha:" + sessionId);

        Admin admin = this.adminService.login(aId, aPassword);
        if (admin != null) {
            Map<String,String> map = new HashMap<>();
            map.put("aName", admin.getAName());
            map.put("aId", String.valueOf(admin.getAId()));
            String token = JwtUtil.getToken(map);
            map.put("token", token);
            return ResponseData.success("登录成功", map);
        } else {
            return ResponseData.fail("登录失败，密码或账号错误");
        }
    }

    /**
     * 分页模糊查询所有医护人员信息
     */
    @RequestMapping("/findAllDoctors")
    public ResponseData findAllDoctors(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size, @RequestParam(value = "query") String query){
        return ResponseData.success("返回医护人员信息成功",  this.doctorService.findAllDoctors(pageNumber, size, query));
    }
    /**
     * 根据id查找医生
     */
    @RequestMapping("/findDoctor")
    public ResponseData findDoctor(@RequestParam(value = "dId") int dId) {
        return ResponseData.success("查询医生信息成功", this.doctorService.findDoctor(dId));
    }
    /**
     * 增加医生信息
     */
    @RequestMapping("/addDoctor")
    public ResponseData addDoctor( Doctor doctor) {
        if (this.doctorService.addDoctor(doctor)) {
            return ResponseData.success("增加医生信息成功");
        }
        return ResponseData.fail("增加医生信息失败！账号或已被占用");
    }
    /**
     * 删除医生信息
     */
    @RequestMapping("/deleteDoctor")
    public ResponseData deleteDoctor(@RequestParam(value = "dId") int dId) {
        Boolean bo = this.doctorService.deleteDoctor(dId);
        if (bo){
            return ResponseData.success("删除医生信息成功");
        }
        return ResponseData.fail("删除医生信息失败");
    }
    /**
     * 修改医生信息
     */
    @RequestMapping("/modifyDoctor")
    public ResponseData modifyDoctor( Doctor doctor) {
        if(this.doctorService.modifyDoctor(doctor))
            return ResponseData.success("修改医生信息成功");
        else
            return ResponseData.fail("修改医生信息失败");
    }
    /**
     * 分页模糊查询所有患者信息
     */
    @RequestMapping("/findAllPatients")
    public ResponseData findAllPatients(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size, @RequestParam(value = "query") String query){
        return ResponseData.success("返回患者信息成功",  this.patientService.findAllPatients(pageNumber, size, query));
    }
    /**
     * 删除患者信息
     */
    @RequestMapping("/deletePatient")
    public ResponseData deletePatient(@RequestParam(value = "pId") int pId) {
        if (this.patientService.deletePatient(pId)){
            return ResponseData.success("删除患者信息成功");
        }
        return ResponseData.fail("删除患者信息失败");
    }
    /**
     * 分页模糊查询所有挂号信息
     */
    @RequestMapping("/findAllOrders")
    public ResponseData findAllOrders(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "size") int size, @RequestParam(value = "query") String query){
        return ResponseData.success("返回挂号信息成功",  this.orderService.findAllOrders(pageNumber, size, query));
    }
    /**
     * 删除挂号信息
     */
    @RequestMapping("/deleteOrder")
    public ResponseData deleteOrder(@RequestParam(value = "oId") int oId) {
        if (this.orderService.deleteOrder(oId)){
            return ResponseData.success("删除挂号信息成功");
        }
        return ResponseData.fail("删除挂号信息失败");
    }

}
