<template>
    <main>
        <h2>상세조회</h2>
        <div id="mainArea">
            
            <div id="boardArea">
                <div>
                    <h3 style="font-weight:bold">{{ board.title }}</h3>
                </div>
                <div id="boardInfo">
                   작성자: {{ board.userName }} | 작성일 : {{ board.createdAt }}
                </div>
                <div id="boardContent">
                    {{board.content}}
                </div>
            </div>
            <div id="btnArea">
                <button class="btn btn-success" v-if="isLoggedIn && userInfo.username === board.userEmail" @click="editBtnClick(board.id)">수정하기</button>
                <button class="btn btn-success" v-if="isLoggedIn && userInfo.username === board.userEmail" @click="delBtnClick(board.id)">삭제</button>
                <button @click="btnClick" class="btn btn-success">뒤로가기</button>
            </div>
            <div id="commentArea">
                <div>댓글 작성자</div>
                <div>댓글 내용</div>
            </div>
        </div>

    </main>
</template>

<script setup>
    import apiClient from '@/api';
    import { reactive } from 'vue';
    import { onMounted, ref } from 'vue'; 
    import { useRoute, useRouter } from 'vue-router';
    import { useAuthStore } from '@/stores/auth';
    
    const authStore = useAuthStore();
    const isLoggedIn = authStore.isLoggedIn;
    const userInfo = authStore.userInfo;
    const router = useRouter();
    const currentRoute = useRoute();
    const board = reactive({});
    const userEmail = localStorage.getItem('userInfo');
    const fetchBoard = async(id)=>{
        try {
            const response = await apiClient.get(`/board/${id}`);
            console.log(response.data);
  
            Object.assign(board, response.data);
        } catch (error) {
            
        }
    };
    const btnClick = ()=>{
        router.push({name:'board'});
    };

    const editBtnClick = (id)=>{
        console.log(id); 
        
        router.push({name:'board/edit/id', params:{id}});
    };

    const delBtnClick = async(id)=>{
        try {

            if(confirm("삭제????")){
                const response = await apiClient.delete(`/board/${id}`); 
                
                if(response.status === 200){
                    alert(`정상적으로 삭제`);
                    router.push({name:'board'});
                }
            }else{
                router.back();
            }

            
        } catch (error) {
            if (error.response.data.code === 403) {
                alert(`권한이 없는 사용자임`);
            }else if(error.response.data.code === 404){
                alert(error.response.data.message);
                router.push({name:'departments'});
            }else{
                alert('에러가 발생');
            }
        }
    };

    onMounted(function(){
        fetchBoard(currentRoute.params.id);
    });
    
</script>

<style scoped>
#mainArea{
    margin-top: 5%;
    display: grid;
    grid-template-rows: 5fr 1fr 4fr;

}

#boardArea{
    display: grid;
    grid-template-rows: 2fr 0.3fr 5fr;
    padding: 5%;
    height: 500px;
    border: 2px solid lightgray;
    box-shadow: 1px 2px 3px 0px;
    border-top-left-radius: 20px;
    border-bottom-right-radius: 20px;
}

#boardInfo{
    color: gray;
    height: 50px;
    margin-bottom: 0%;
    border-bottom: 2px solid rgb(51, 116, 51);
}
#boardContent{
    margin-top: 3%;
   
}

#btnArea{
    margin-top: 3%; 
    
}
</style>