<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/component/demogrid.jsp" %> 
<script>
Vue.component('scouter-total-stat',{
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


            rediskey:'',
			mapdata:{},
			isobjmap:{},
			key_list:[],
			key_list_item:[],
			field:'',
			adddata:'',
			rsltmsg:'',

			searchQuery: '',
            gridColumns: ['svcTgDate', 'svcNm', 'svcUrl', 'svcCnt', 'svcErrCnt', 'svcTotalElap', 'svcAvgElap', 'svcRgDt'],
            gridData: [
                    {
                    svcTgDate: '123',
                    svcNm: '123 ',
                    svcUrl: ' 123',
                    svcCnt: '13 ',
                    svcErrCnt: '123 ',
                    svcTotalElap: '123 ' ,
                    svcAvgElap: ' 123',
                    svcRgDt:'13'
                    }
                ]
	      }
	    },methods: { //페이지 시작하면은 자동 함수 실행
          		getScoutData ( ) {
                           			this.rsltmsg = '처리 중';

                           			let data =JSON.stringify({


                           		    })
                           			const config = {
                           					  headers: {
                           					    'Content-Type': 'application/json'
                           					  }
                           			}


                           			axios.get('redis/scoutTotalStat', config)
                           			  .then((response) => {
                                        console.log(response);
                           				if(response.data.rslt == "1000"){
                           				    console.log(response);
                           					this.gridData = response.data.data;
                           					console.log(response.data.data);
                           					this.rsltmsg = '조회 성공';
                           					this.$forceUpdate();
                           				}else{
                           					this.rsltmsg = '조회 실패';
                           					this.$forceUpdate();
                           				}
                           			}).catch((ex)=> {
                           				console.log("ERR!!!!! : ", ex)
                           			})

                           		}
        },
	    created() {
            this.getScoutData ( );

	    }
})

</script>


