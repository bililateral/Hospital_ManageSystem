import Vue from "vue";
import VueRouter from "vue-router";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import "@/assets/css/global.css";
import Login from "@/views/Login.vue";
import Admin from "@/views/Admin.vue";
import Doctor from "@/views/Doctor.vue";
import Patient from "@/views/Patient.vue";
import PatientList from "@/views/PatientList.vue";
import DoctorList from "@/views/DoctorList.vue";
import OrderList from "@/views/OrderList.vue";
import {getToken} from "@/utils/storage.js";
import OrderOperate from "@/views/OrderOperate.vue";
import SectionMessage from "@/views/SectionMessage.vue";
import MyOrder from "@/views/MyOrder.vue";
import OrderToday from "@/views/OrderToday.vue";
import DealOrder from "@/views/DealOrder.vue";
import DrugList from "@/views/DrugList.vue";
import CheckList from "@/views/CheckList.vue";
import DoctorOrder from "@/views/DoctorOrder.vue";
import InBed from "@/views/InBed.vue";
import ArrangeIndex from "@/views/ArrangeIndex.vue";
import SectionList from "@/views/SectionList.vue";
import AdminLayout from "@/views/AdminLayout.vue";
import DoctorLayout from "@/views/DoctorLayout.vue";
import PatientLayout from "@/views/PatientLayout.vue";
import SectionIndex from "@/views/SectionIndex.vue";
import ArrangeDoctor from "@/views/ArrangeDoctor.vue";
import MyBed from "@/views/MyBed.vue";
import BedList from "@/views/BedList.vue";
import DataExpore from "@/views/DataExpore.vue";
import echarts from 'echarts';//引入echarts
import DealOrderAgain from "@/views/DealOrderAgain.vue";
import DoctorCard from "@/views/DoctorCard.vue";
import PatientCard from "@/views/PatientCard.vue"

Vue.prototype.$echarts = echarts;//引入echarts
Vue.use(ElementUI);
Vue.use(VueRouter);

const routes = [
  {
    path: "*",
    redirect:"/login"   //设置默认跳转路径

  },
  {
    path: "/login",
    component: Login
  },
  {
    path: "/admin",
    component: Admin,
    meta: {
      requireAuth: true,  // 该路由项需要权限校验
    },
    children:[
      {
        path: "/adminLayout",
        component: AdminLayout,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
      path: "/doctorList",
      component: DoctorList,
      meta: {
        requireAuth: true,  // 该路由项需要权限校验
      },
    },
    {
      path: "/patientList",
      component: PatientList,
      meta: {
        requireAuth: true,  // 该路由项需要权限校验
      },
    },
    {
      path: "/orderList",
      component: OrderList,
      meta: {
        requireAuth: true,  // 该路由项需要权限校验
      },
    },
    {
      path: "/drugList",
      component: DrugList,
      meta: {
        requireAuth: true,  // 该路由项需要权限校验
      },
    },
    {
      path: "/checkList",
      component: CheckList,
      meta: {
        requireAuth: true,  // 该路由项需要权限校验
      },
      
    },
    {
      path: "/bedList",
      component: BedList,
      meta: {
        requireAuth: true,  // 该路由项需要权限校验
      },
      
    },
    {
      path: "/dataExpore",
      component: DataExpore,
      meta: {
        requireAuth: true,  // 该路由项需要权限校验
      },
      
    },
    {
      path: "/arrangeIndex",
      component: ArrangeIndex,
      meta: {
        requireAuth: true,  // 该路由项需要权限校验
      },
      children:[
        {
          path: "/sectionIndex",
          component: SectionIndex,
          meta: {
            requireAuth: true,  // 该路由项需要权限校验
          },
        },
        {
          path: "/arrangeDoctor",
          component: ArrangeDoctor,
          meta: {
            requireAuth: true,  // 该路由项需要权限校验
          },
        },
      ]
      
    },
    {
      path: "/sectionList",
      component: SectionList,
      meta: {
        requireAuth: true,  // 该路由项需要权限校验
      },
     
      
    },
  ]
  },
  {
    path: "/patient",
    component: Patient,
    meta: {
      requireAuth: true,  // 该路由项需要权限校验
    },
    children:[
      {
        path: "/patientLayout",
        component: PatientLayout,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
        path: "/orderOperate",
        component: OrderOperate,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
        path: "/sectionMessage",
        component: SectionMessage,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
        path: "/myOrder",
        component: MyOrder,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
        path: "/myBed",
        component: MyBed,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
        path: "/patientCard",
        component: PatientCard,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      }
    ]
  },
  {
    path: "/doctor",
    component: Doctor,
    meta: {
      requireAuth: true,  // 该路由项需要权限校验
    },
    children:[
      {
        path: "/doctorLayout",
        component: DoctorLayout,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
        path: "/orderToday",
        component: OrderToday,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
        path: "/dealOrder",
        component: DealOrder,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
       path: "/dealOrderAgain",
        component: DealOrderAgain,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
        path: "/doctorOrder",
        component: DoctorOrder,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
        path: "/inBed",
        component: InBed,
        meta: {
        requireAuth: true,  // 该路由项需要权限校验
        },
      },
      {
        path: "/doctorCard",
        component: DoctorCard,
        meta: {
          requireAuth: true,  // 该路由项需要权限校验
        },
      }
    ],
  }
];

const router = new VueRouter({
  routes
});
//没登录的情况下，访问任何一个页面都会返回登录页面
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    const token = getToken();
    if (token !== null) {
      //直接放行
      next();
  } else {
      // 保存目标路径到本地存储
      localStorage.setItem('targetUrl', to.fullPath);
      next("/login");
    }
  }
  else{ //不需要授权，直接放行
    next();
  }
  });
export default router;
