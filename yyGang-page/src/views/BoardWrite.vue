<template>
    <main>
        <div id="mainArea">
            <h2 id="boardTitle">게시판 작성</h2>
            <BoardForm @form-submit="formSubmit" submit-button-text="등록" id="boardContent"/>
        </div>
    </main>
</template>

<script setup>
    import apiClient from '@/api';
    import BoardForm from '@/components/board/BoardForm.vue';
    import { useRouter } from 'vue-router';

    const router = useRouter();

    const formSubmit= async(formData)=>{
        try {
            const response = await apiClient.post(`/board`,formData);

            console.log("formData==" + formData.title);
            console.log("formData==" + formData.content);
            

            if(response.status === 201){
                alert(`정상적으로 등록`);

                router.push({name:'board'});
            }
        } catch (error) {
            
        }
    }

</script>


<style>
#boardTitle{
    border-bottom: 2px solid rgb(51, 116, 51);
}
#mainArea{
    margin-top: 5%;
    display: grid;
    grid-template-rows: 2fr 4fr;
}
#boardContent{
    margin-top: 3%;
}
</style>