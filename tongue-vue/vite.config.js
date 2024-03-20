import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'node:path'

export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  //跨域解决
  server:{
    // proxy:{
    //   //获取路径中包含/api的请求
    //   '/api':{
    //     target:"http://localhost:8080",//后台服务器所在源
    //     changeOrigin:true,//修改源
    //     rewrite:(path)=>path.replace(/^\/api/,'')//api替换为空字符串
    //   }

    // }
    host:'0.0.0.0'
  }
})
