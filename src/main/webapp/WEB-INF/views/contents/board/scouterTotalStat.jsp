<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/component/demogrid.jsp" %> 
<script>
Vue.component('scouter-total-avg',{
      template : `	
<div id="demo">
  <form id="search">
    Search <input name="query" v-model="searchQuery">
  </form>
  <demo-grid
    :heroes="gridData"
    :columns="gridColumns"
    :filter-key="searchQuery">
  </demo-grid>
</div>
	  `,
	  data() {
	      return {
			 searchQuery: '',

                    gridColumns: ['svcTgDate', 'svcNm', 'svcUrl', 'svcCnt', 'svcErrCnt', 'svcTotalElap', 'svcAvgElap', 'svcRgDt'],
                    gridData: [
                         { svcTgDate: '7', svcNm: 'member', svcUrl: 'delivery/change<POST>', svcCnt: 6, svcErrCnt: 3, svcTotalElap: 444 , svcAvgElap: 666, svcRgDt:'20201122'},
                         { svcTgDate: '7', svcNm: 'order', svcUrl: 'delivery/change<POST>', svcCnt: 2, svcErrCnt: 2, svcTotalElap: 244 , svcAvgElap: 266, svcRgDt:'20201122'}
                    ]
	      }
	    },

	    methods: {
	       
	  
	    }
})

</script>


