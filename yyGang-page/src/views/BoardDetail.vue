<template>
    <main>
        <div id="mainArea">
            <div id="boardArea">
                <div>
                    <h3 style="font-weight:bold">{{ board.title }}</h3>
                </div>
                <div id="boardInfo">
                   작성자: {{ board.userName }} | 작성일 : {{ (board.createdAt) }}
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
            <div class="mt-2 p-3 bg-body rounded shadow-sm">
                <h4 id="commentTitle">댓글 {{comment.length }}개</h4>

                <h2 class="fs-6">댓글 쓰기</h2>

                <input class="form-control" type="text" v-model.trim="newComment">
                <div class="form-text">존중과 배려하는 마음으로 건강한 댓글 문화를 함께 만들어가요.</div>

                <div class="d-flex justify-content-end">
                    <button class="btn btn-success" @click="addComment(comment.id)">등록</button>
                </div>
                
                <div id="commentArea">
                    <div v-for="cmt in comment" :key="cmt.id" class="comment-item">
                        <div class="comment-header">
                            <span @click="toggleReplyInput(cmt.id)" class="comment-info">
                                <span class="comment-user">{{ cmt.userId }}</span> | 
                                <span class="comment-date">{{ formatTime(cmt.createdAt) }}</span>
                            </span><br>
                            <strong class="comment-content">{{ cmt.content }}</strong>
                        </div>
                        
                        <!-- 대댓글 입력 폼 -->
                        <div v-if="replyTarget === cmt.id" class="reply-form">
                            <input class="form-control" type="text" v-model.trim="replyComment" @keyup.enter="addComment(cmt.id)" placeholder="답글을 입력하세요...">
                            <button class="btn btn-sm btn-outline-success mt-1" @click="addComment(cmt.id)">답글 등록</button>
                        </div>
                        
                        <!-- 대댓글 목록 -->
                        <div v-for="x in cmt.childComments" :key="x.id" class="child-comment">
                            <div class="child-comment-header">
                                <span class="comment-user">{{ x.userId }}</span> | 
                                <span class="comment-date">{{ formatTime(x.createdAt) }}</span>
                            </div>
                            <strong class="child-comment-content">{{ x.content }}</strong>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </main>
</template>

<script setup>
    import apiClient from '@/api';
    import { reactive, toRaw } from 'vue';
    import { onMounted, ref } from 'vue'; 
    import { useRoute, useRouter } from 'vue-router';
    import { useAuthStore } from '@/stores/auth';
    
    const authStore = useAuthStore();
    const isLoggedIn = authStore.isLoggedIn;
    const userInfo = authStore.userInfo;
    const router = useRouter();
    const currentRoute = useRoute();
    const board = reactive({});
    const comment = ref([]);
    const newComment = ref(""); // 새 댓글 입력 필드
    const replyComment = ref(""); // 대댓글 입력 필드
    const replyTarget = ref(null); // 대댓글 입력 대상

    const userEmail = localStorage.getItem('userInfo');

    
    const fetchBoard = async(id)=>{
        try {
            const response = await apiClient.get(`/board/${id}`);
            
            // console.log(response.data);
  
            Object.assign(board, response.data);
        } catch (error) {
            
        }
    };

    const fetchComment = async(id)=>{
        try {
            const response = await apiClient.get(`/board/${id}/comment`);

            console.log(response.data);

            comment.value = await response.data;

        } catch (error) {
            
        }

    };

    

    // 부모 댓글 클릭 시 대댓글 입력 필드 토글
    const toggleReplyInput = (parentId) => {
        replyTarget.value = replyTarget.value === parentId ? null : parentId;
        replyComment.value = ""; // 입력 필드 초기화
    };


    // 댓글 추가 기능
    const addComment = async (parentId) => {

        console.log("입력된 댓글 내용:", newComment.value);
        const content = parentId ? replyComment.value : newComment.value;

        console.log("parentId:", parentId);
        console.log("댓글 내용:", content);  // content 값 출력

        if (!content.trim()) {
            return alert("댓글을 입력하세요!");
        }


        try {
            console.log("보내는 데이터:", { content: content.trim(), parentId });
            const response = await apiClient.post(`/board/${board.id}/comment`, 
                { content: content.trim() }, 
                { params: parentId ? { parentId } : {} } // parentId가 있을 경우만 추가
            );

            console.log("서버 응답:", response.data);

            if (response.status === 201) {
                 // 댓글 목록 다시 불러오기
                await fetchComment(board.id);

                 // 입력 필드 초기화
            if (parentId) {
                replyComment.value = "";
                replyTarget.value = null;
            } else {
                newComment.value = "";
            }

            //      // 새로운 댓글 또는 대댓글 추가
            //     if (parentId) {
            //         const parentComment = comment.value.find(cmt => cmt.id === parentId);
            //         if (parentComment) parentComment.childComments.push(response.data);
            //         replyComment.value = "";
            //         replyTarget.value = null;
            //     } else {
            //         comment.value.push(response.data);
            //         newComment.value = "";
            //     }
            }
        } catch (error) {
            console.error("댓글 추가 실패", error);
            alert("댓글 추가 중 오류 발생");
        }
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
    const btnClick = ()=>{
        router.push({name:'board'});
    };

    const editBtnClick = (id)=>{
        console.log(id); 
        
        router.push({name:'board/edit/id', params:{id}});
    };
    const formatTime = (dateString) => {
        const date = new Date(dateString);
  
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더해야 합니다.
        const day = String(date.getDate()).padStart(2, '0');
        
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        
        return `${year}-${month}-${day} ${hours}:${minutes}`;
    };

    onMounted(function(){
        fetchBoard(currentRoute.params.id);
        fetchComment(currentRoute.params.id)
    });
    
</script>

<style scoped>
#mainArea{
    margin-top: 5%;
    display: grid;
    grid-template-rows: 2fr 0.2fr 3fr;

}

#boardArea{
    /* display: grid; */
    /* grid-template-rows: 2fr 0.3fr 5fr; */
    padding: 5%;
    /* height: 500px; */
    border: 1px solid #ddd;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
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

#commentArea {
    margin-top: 20px;
    padding: 10px;
}

.comment-item {
    padding: 10px;
    margin-bottom: 10px;
    background-color: #f9f9f9;
    border-radius: 5px;
    border: 1px solid #ddd;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.comment-header {
    font-size: 14px;
    margin-bottom: 8px;
    color: #333;
}

.comment-info {
    cursor: pointer;
    font-size: 12px;
    color: gray;
}

.comment-user {
    font-weight: bold;
}

.comment-date {
    font-size: 12px;
    color: #888;
}

.comment-content {
    font-size: 16px;
    line-height: 1.5;
}

.reply-form {
    margin-top: 10px;
    padding-left: 20px;
    font-size: 14px;
}

.reply-form input {
    width: 100%;
    max-width: 400px;
}

.child-comment {
    margin-top: 10px;
    padding-left: 20px;
    background-color: #f1f1f1;
    border-left: 3px solid #5db26c;
    margin-bottom: 10px;
}

.child-comment-header {
    font-size: 12px;
    color: #555;
}

.child-comment-content {
    font-size: 14px;
    color: #444;
    line-height: 1.4;
}
</style>