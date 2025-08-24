package com.rabbiter.hospital.controller;

import com.rabbiter.hospital.mapper.OrderMapper;
import com.rabbiter.hospital.pojo.Doctor;
import com.rabbiter.hospital.pojo.Orders;
import com.rabbiter.hospital.pojo.Patient;
import com.rabbiter.hospital.service.DoctorService;
import com.rabbiter.hospital.service.OrderService;
import com.rabbiter.hospital.service.PatientService;
import com.rabbiter.hospital.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private OrderService orderService;
    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private CaptchaRedisUtil captchaRedisUtil;

    // PatientController添加验证码接口
    @RequestMapping("/getCaptcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = CaptchaUtil.generateCode();
        String sessionId = request.getSession().getId();
        captchaRedisUtil.setCaptcha("patient:captcha:" + sessionId, code);

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
    public ResponseData login(@RequestParam(value = "pId") int pId,
                              @RequestParam(value = "pPassword") String pPassword,
                              @RequestParam("captcha") String captcha,
                              HttpServletRequest request) {
        // 验证码验证逻辑
        String sessionId = request.getSession().getId();
        String storedCode = captchaRedisUtil.getCaptcha("patient:captcha:" + sessionId);

        if (storedCode == null || !storedCode.equalsIgnoreCase(captcha)) {
            return ResponseData.fail("验证码错误或已过期");
        }

        captchaRedisUtil.deleteCaptcha("patient:captcha:" + sessionId);

        Patient patient = this.patientService.login(pId, pPassword);
        if (patient != null) {
            Map<String,String> map = new HashMap<>();
            map.put("pName", patient.getPName());
            map.put("pId", String.valueOf(patient.getPId()));
            map.put("pCard", patient.getPCard());
            String token = JwtUtil.getToken(map);
            map.put("token", token);
            //response.setHeader("token", token);
            return ResponseData.success("登录成功", map);
        } else {
            return ResponseData.fail("登录失败，密码或账号错误");
        }
    }
    /**
     * 根据科室查询所有医生信息
     */
    @RequestMapping("/findDoctorBySection")
    public ResponseData findDoctorBySection(@RequestParam(value = "dSection") String dSection){
        return ResponseData.success("根据科室查询所有医生信息成功", this.doctorService.findDoctorBySection(dSection));
    }
    /**
     * 增加挂号信息
     */
    @RequestMapping("/addOrder")
    public ResponseData addOrder(Orders order, String arId){
        System.out.println(arId);
        if (this.orderService.addOrder(order, arId))
            return ResponseData.success("插入挂号信息成功");
        return ResponseData.fail("插入挂号信息失败");
    }
    /**
     * 根据pId查询挂号
     */
    @RequestMapping("/findOrderByPid")
    public ResponseData findOrderByPid(@RequestParam(value = "pId") int pId){
        return ResponseData.success("返回挂号信息成功", this.orderService.findOrderByPid(pId)) ;
    }

    /**
     * 接收注册的患者信息
     */
    @PostMapping("/addPatient")
    public ResponseData addPatient(@RequestBody Patient patient) {
        Boolean bo = this.patientService.addPatient(patient);
        if (bo)
            return ResponseData.success("注册成功");
        return ResponseData.fail("注册失败！账号或邮箱已被占用");
    }
    /**
     * 导出PDF
     */
    @GetMapping("/pdf")
    public void downloadPDF(HttpServletRequest request, HttpServletResponse response, int oId) throws Exception {
        Orders order = this.orderMapper.findOrderByOid(oId);
        Doctor doctor = doctorService.findDoctor(order.getdId());
        double registrationFee = doctor != null ? doctor.getdPrice() : 0;
        PdfUtil.ExportPdf(request, response, order,registrationFee);
    }
    /**
     * 统计患者男女人数
     */
    @RequestMapping("/patientAge")
    public ResponseData patientAge(){
        return  ResponseData.success("统计患者男女人数成功", this.patientService.patientAge());
    }
}
