<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/component/demogrid.jsp" %> 
<script>


Vue.component('scouter-avgSev-stat',{
      template : `
<div id="demo">
  <form id="search">
    Search <input name="query" v-model="searchQuery">
  </form>
  <demo-grid
    :heroes="gridData"
    :columns="gridColumns"
    :headers="gridHeader"
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
            gridColumns: [
                'tgDate', 'resDlyRate', 'errRate', 'tps', 'avlAbility', 'errCnt', 'totalDlyTime', 'resDlyCnt', 'svcUnAvail'
              ],

            gridHeader:

                { tgDate:  '날짜',
                 resDlyRate: '응답 지연 율',
                 errRate: '에러 율',
                 tps: 'tps',
                 avlAbility: '가용 율',
                 errCnt: '에러 수 ',
                 totalDlyTime: '전체 지연 시간',
                 resDlyCnt: '응답 지연 수 ',
                 svcUnAvail: '서비스 불가(분)'
                }
            ,
            gridData: [

            {
                    svcTgDate: '',
                    svcNm: '',
                    svcUrl: '',
                    svcCnt: '',
                    svcErrCnt: '',
                    svcTotalElap: '' ,
                    svcAvgElap: '',
                    svcRgDt:''
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


                           			axios.get('redis/scoutAvgStat', config)
                           			  .then((response) => {
                                        console.log(response);
                           				if(response.data.rslt == "1000"){
                           				    console.log(response);

                           					this.gridData = Object.values(response.data.data);
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

