<template>
    <div>
        <!-- 删除提示框 -->
        <el-dialog title="提示" v-model="centerDialogVisible" width="300px" center>
            <div class="del-dialog-cnt" align="center">删除不可恢复，是否确定删除？</div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="cancelRow">取 消</el-button>
                    <el-button type="primary" @click="confirm">确 定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
    
<script setup>
import { ref, toRefs, watch } from 'vue'

const props = defineProps({
    delVisible: Boolean,
})

const emits = defineEmits(['cancelRow', 'confirm'])

const { delVisible } = toRefs(props);
const centerDialogVisible = ref(delVisible.value);
 watch(delVisible, (value) => {
    centerDialogVisible.value = value
 })

 function cancelRow() {
    emits('cancelRow', false)
 }

 function confirm() {
    emits('confirm', null)
 }

 

</script>
    
<style lang='stylus' scoped></style>
    