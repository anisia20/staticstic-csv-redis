<%@ page language="java" pageEncoding="utf-8" %>
<script>
const redishash = Vue.component('redishash',{
   template : `	
    <div>
  		<table>
  			<tr>
  				<td colspan="3"><h3>REDIS DATA 수정</h3></td>
  			</tr>
  			<tr height="30">
  				<td>레디스 키명 </td>
  				<td>REDISKEY </td>
  				<td colspan="2">
  					<input id="rediskey" type="text" name="rediskey" maxlength="100" v-model="rediskey"/>
  					<input type="button" @click="getkeylist" id="getkeylist" value="요청"/>
  				</td>
  			</tr>
  			<tr height="30">
				<td>키명 </td>
				<td>KEY </td>
				<td colspan="2">
					<input id="key_list" type="text" name="key_list" maxlength="100" v-model="key_list"/>
				</td>
			</tr>
  			<tr>
  				<td colspan="2" align="left" height="50"><strong><font size="2">KEY LIST</font></strong></td>
  				<td>
	  				<select	name="sample_type" id="key_list" @change="getdata" v-model="key_list" multiple>
						<option v-for="field in key_list_item" v-bind:value="field">{{ field }}</option>
					</select> 
					</br>
					<input type="button" @click="getdata" value="조회"/>
					<input type="button" @click="setdatas" id="setdatas" value="추가/수정"/>
  				</td>
  				<td>
					<table>
						<tr>
							<td>
							<input type="text" v-model="adddata"/>
							<a href="javascript:;" v-on:click="addfield" class="btn btn-sm btn-primary">필드 추가</a>
							</td>
						</tr>
						<tr style='border: 1px solid black;' v-for="(value, key) in mapdata">
							<td v-bind:key="key">{{ key }}</td>
							<td colspan="2" style='width:20em; font-weight: bold; "+bgcolor+"'>
							<textarea @change="chtextarea(mapdata[key],key)" style="resize: both;" v-if="isobj(value,key)" v-model="mapdata[key]"></textarea>
							<input type="text" v-model="mapdata[key]" @change="chtextarea(mapdata[key],key)" v-else></input>
							<a href="javascript:;" v-on:click="deletefield(key)" class="btn btn-sm btn-primary">삭제</a>
							</td>
						</tr>
					</table>
				</td>
				
  			</tr>
  		</table>
  </div>
`,
	  data(){
     	 return{
     		rediskey:'',
			mapdata:{},
			isobjmap:{},
			key_list:[],
			key_list_item:[],
			field:'',
			adddata:'',
			rsltmsg:''
		}
	  },
	  mounted: function () {
	  },
	  filters: {
	  },
	  computed: {
	  },
	  methods:{
		  isobj:function(value, key) {
			  if(value instanceof Object){
				var jsonstr = JSON.stringify(value, null, 2);
				this.mapdata[key]=jsonstr;
				this.isobjmap[key]=true;
			  }
			  return this.isobjmap[key];
	  },
		  chtextarea:function(value, key) {
			  try{
				  this.mapdata[key]=JSON.parse(value);
			  }catch(e){
				  this.mapdata[key]=value;
				  delete this.isobjmap[key];
			  }
			  this.$forceUpdate();
		  },
		getkeylist:function(e) {
			axios({
				method: 'GET',
				url : 'redis/redisHashList/'+this.rediskey,
				contentType : "application/x-www-form-urlencoded;charset=utf-8"
			}).then((response) => {
				if(response.data.rslt == "1000"){
					this.key_list_item = response.data.data.keylist;
		    		delete this.mapdata; 
				}else{
					this.rsltmsg = '키조회 실패';
				}
			}).catch((ex)=> {
				console.log("ERR!!!!! : ", ex)
			})
			
		},
		setdatas:function(e) {
			this.rsltmsg = '처리 중';
			if(!confirm('저장하시겠습니까?')){return;}
			for (var key in this.isobjmap) {
				this.mapdata[key]=JSON.parse(this.mapdata[key]);
			}
			
			let data =JSON.stringify({
				data: this.mapdata
		    })
			const config = {
					  headers: {
					    'Content-Type': 'application/json'
					  }
					}

			axios.post('redis/setRedisHash/'+this.rediskey+'/'+this.key_list,data,config).then((response) => {
				if(response.data.rslt == "1000"){
					this.rsltmsg = '성공';
				}else{
					this.rsltmsg = '실패';
				}
				this.getkeylist();
				this.getdata();
			}).catch((ex)=> {
				console.log("ERR!!!!! : ", ex)
			})
		},
		deldata:function(e) {
			this.rsltmsg = '처리 중';
			if(!confirm('해당키 데이터가 삭제 됩니다.')){return;}
			axios({
				method: 'GET',
				url : 'redis/delRedisHash/'+this.rediskey+'/'+this.key_list,
				contentType : "application/x-www-form-urlencoded;charset=utf-8"
			}).then((response) => {
				if(response.data.rslt == "1000"){
					this.rsltmsg = '성공';
				}else{
					this.rsltmsg = '실패';
				}
				this.getkeylist();
			}).catch((ex)=> {
				console.log("ERR!!!!! : ", ex)
			})
		},
		
		getdata:function(e) {
			this.rsltmsg = '처리 중';

			let data =JSON.stringify({
				data: {rediskey: this.rediskey, key_list: this.key_list[0] }
		    })
			const config = {
					  headers: {
					    'Content-Type': 'application/json'
					  }
					}


			axios.post('redis/getRedisHash',data,config).then((response) => {
				if(response.data.rslt == "1000"){
					this.mapdata = response.data.data;
					this.rsltmsg = '조회 성공';
					this.$forceUpdate();
				}else{
					this.rsltmsg = '조회 실패';
					this.$forceUpdate();
				}
			}).catch((ex)=> {
				console.log("ERR!!!!! : ", ex)
			})
			
		},
		addfield:function() {
			this.mapdata[this.adddata]=''; 
    		this.$forceUpdate();	    	
    	},
    	deletefield:function(key) {
    		delete this.mapdata[key];
    		delete this.isobjmap[key]; 
    		this.$forceUpdate();	    	
    	}
      }
      
})

</script>