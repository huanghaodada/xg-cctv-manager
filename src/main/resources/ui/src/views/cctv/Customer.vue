<template>
  <div class="app-container">
    <el-form :inline="true">
      <!--卡号-->
      <el-form-item :label="$t('cctv.cardNumber')">
        <el-input v-model="q.cardNumber" :placeholder="$t('cctv.pe_cardNumber')" />
      </el-form-item>
      <!--位置-->
      <el-form-item :label="$t('cctv.location')">
        <el-input v-model="q.location" :placeholder="$t('cctv.pe_location')" />
      </el-form-item>

      <!--状态-->
      <el-form-item :label="$t('cctv.status')">
        <el-switch
          v-model="q.status"
          :active-value="0"
          :inactive-value="1"
          :active-text="$t('cctv.disable')"
          :inactive-text="$t('cctv.normal')">
        </el-switch>
      </el-form-item>

    </el-form>

    <el-form :inline="true">
      <el-form-item>
        <!-- 搜索按钮 -->
        <el-button type="primary" @click="doSearch = true">
          {{$t('cctv.search')}}
        </el-button>
      </el-form-item>

      <!-- 导出按钮 -->
      <el-form-item>
        <el-button type="info" @click="handleExcel">
          {{$t('cctv.exportExcel')}}
        </el-button>
      </el-form-item>

      <el-form-item>
        <!-- 重置按钮 -->
        <el-button @click="resetQueryData">
          {{$t('cctv.reset')}}
        </el-button>
      </el-form-item>

      <el-form-item>
        <!-- 新增用户按钮 -->
        <el-button type="info" @click="handleAdd">
          {{$t('cctv.new')}}
        </el-button>
      </el-form-item>
    </el-form>

    <customer-page
      :query="q"
      :do-search.sync="doSearch"
      :handle-edit="handleEdit"
      :handle-delete="handleDelete"
    />

    <el-dialog :visible.sync="showMark" :title="dialogType==='edit'?'Edit':'New'">
      <el-form :model="d" :ref="formName" :rules="rules" label-width="80px" label-position="left">
        <!--名称-->
        <el-form-item :label="$t('cctv.name')" prop="name">
          <el-input v-model="d.name" :placeholder="$t('cctv.pe_name')" />
        </el-form-item>
        <!--卡号-->
        <el-form-item :label="$t('cctv.cardNumber')" prop="cardNumber">
          <el-input v-model="d.cardNumber" :placeholder="$t('cctv.pe_cardNumber')" />
        </el-form-item>
        <!--位置-->
        <el-form-item :label="$t('cctv.location')" prop="location">
          <el-input v-model="d.location" :placeholder="$t('cctv.pe_location')" />
        </el-form-item>
        <!--平均下注-->
        <el-form-item :label="$t('cctv.avgBetting')" prop="avgBetting">
          <el-input v-model="d.avgBetting" :placeholder="$t('cctv.pe_avgBetting')" />
        </el-form-item>
        <!--国籍-->
        <el-form-item :label="$t('cctv.nationality')" prop="nationality">
          <el-input v-model="d.nationality" :placeholder="$t('cctv.pe_nationality')" />
        </el-form-item>
        <!--照片-->
        <el-form-item :label="$t('cctv.image')">
          <avatar-image :url.sync="d.imageUrl"/>
        </el-form-item>
        <!--状态-->
        <el-form-item :label="$t('cctv.status')">
          <el-radio v-model="d.status" :label="0" border>{{$t('cctv.disable')}}</el-radio>
          <el-radio v-model="d.status" :label="1" border>{{$t('cctv.normal')}}</el-radio>
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="showMark=false">
          {{ $t('cctv.cancel') }}
        </el-button>
        <el-button type="info" @click="reset">
          {{$t('cctv.reset')}}
        </el-button>
        <el-button type="primary" @click="confirm">
          {{ $t('cctv.confirm') }}
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import CustomerPage from './components/CustomerPage'
import AvatarImage from '@/components/Upload/AvatarImage'
import {saveCustomer , deleteCustomerById , updateCustomer , exportCustomerExcel} from '@/api/customer'
import { downloadExcelByKey, deepClone } from "@/utils"
const data = {
  name: null,
  cardNumber: null,
  location: null,
  avgBetting: null,
  nationality: null,
  imageUrl: '',
  status: 0
}
const queryData = {
  cardNumber: null,
  location: null,
  status: null
}
export default {
  name: 'Customer',
  components: { CustomerPage , AvatarImage},
  data() {
    return {
      q: deepClone(queryData),
      d: deepClone(data),
      doSearch: true,
      showMark: false,
      dialogType: 'edit', // 'edit' or 'new'
      formName: 'form',
      rules: {
        name: [{ required: true, trigger: 'blur' , message:'not null'}],
        cardNumber: [{ required: true, trigger: 'blur' , message:'not null'}],
        location: [{ required: true, trigger: 'blur' , message:'not null'}],
        avgBetting: [{ required: true, trigger: 'blur' , message:'not null'}],
        nationality: [{ required: true, trigger: 'blur' , message:'not null'}],
        status: [{ required: true, trigger: 'blur' , message:'not null'}]
      }
    }
  },
  methods: {
    resetQueryData() {
      this.q = deepClone(queryData)
    },
    reset(){
      if (this.$refs[this.formName] != null){
        this.$refs[this.formName].resetFields()
      }
      this.d = deepClone(data)
    },
    handleAdd() {
      this.reset()
      this.showMark = true
      this.dialogType = 'new'
    },
    async handleExcel(){
      const res = await exportCustomerExcel(this.q)
      if (res.code === 0){
        downloadExcelByKey(res.key)
      }
    },
    handleEdit(scope) {
      let clone = deepClone(scope.row)
      this.d = clone
      this.showMark = true
      this.dialogType = 'edit'
    },
    handleDelete({ $index, row }) {
      this.$confirm('Confirm ?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(async() => {
          const res = await deleteCustomerById(row.id)
          if (res.code === 0){
            this.doSearch = true
            this.$message({
              type: 'success',
              message: 'Delete succed!'
            })
          }
        })
        .catch(err => { console.error(err) })
    },
    async submit(){
      let res
      if (this.d.id){
        res = await updateCustomer(this.d)
      }else {
        res = await saveCustomer(this.d)
      }
      if (res.code === 0){
        this.showMark = false
        this.doSearch = true
        this.$message.success('提交成功')
      }
    },
    confirm() {
      this.$refs[this.formName].validate((valid) => {
        if (valid) {
          this.submit();
        }else {
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
  .app-container {

  }

</style>
