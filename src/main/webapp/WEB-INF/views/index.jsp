<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/router/router.jsp" %>
<div id="app">
  <Layout>
        <template #header="self">
            <h1 id="title">{{self.title}}</h1>
            <span id="join">{{self.join}}</span>
            <span id="login">{{self.login}}</span>
        </template>
        <template #sidebar="self" >
            {{self.menu}}
            <ul class="menu">
                <li v-for="i of sidebars" :key="i.menu">
                	<router-link :to="i.url"> {{ i.menu }}</router-link>
                </li>
            </ul>
        </template>
        <template #content="self" >
            {{self.content}}
            <router-view></router-view>
        </template>
        <template #footer="self" >
            <h3 id="footer">{{self.footer}}</h3>
        </template>
    </Layout>
  </p>
  <!-- 라우트 아울렛 -->
  <!-- 현재 라우트에 맞는 컴포넌트가 렌더링됩니다. -->
</div>
 
<script>
const app = new Vue({
	router
	,
	data : ()=>{
        return {
            sidebars: [
                {menu: 'CSV UPLOAD', url: '/uploadcsv'},
                {menu: 'REDIS HASH', url: '/redishash'},
                {menu: 'REDIS BOARD', url: '/redisboard'}
            ]
 
        }
    }
}).$mount('#app')
</script>
<style scoped>
    ul.menu{
        position: relative;
        padding: 5px;
        list-style: none;
        font-style: italic;
    }
    #title{width: 300px;margin: 0 auto}
    #login{margin-right: 50px; float: right}
    #join{margin-right: 50px; float: right}
    #footer{width: 300px; margin: 0 auto}
</style>