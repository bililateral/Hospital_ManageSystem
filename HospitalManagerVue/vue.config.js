/*
 * @Description: 
 * @Author: bililateral
 * @Date: 2025-08-09 20:02:29
 */
module.exports = {
    lintOnSave: false, // 关闭eslint校验
    devServer: {
        host: "localhost",    // 开发服务器运行的主机地址
        port: 9282,           // 开发服务器端口（前端项目运行的端口）
        proxy: "http://localhost:9281",  // 代理目标：后端API服务器地址
        https: false,
        overlay: { // 关闭eslint校验
            warning: false,
            errors: false
        },
    }
}
//设置代理解决跨域问题
