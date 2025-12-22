<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="序号" prop="id">
        <el-input
            v-model="queryParams.id"
            placeholder="请输入序号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下单时间" prop="orderDate">
        <el-date-picker clearable
                        v-model="queryParams.orderDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择下单时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="产品类别" prop="productCategory">
        <el-select v-model="queryParams.productCategory" placeholder="请选择产品类别" clearable>
          <el-option
              v-for="dict in product_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-select v-model="queryParams.customerName" placeholder="请选择客户名称" clearable>
          <el-option
              v-for="dict in customer_name"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="规格型号" prop="specificationModel">
        <el-select v-model="queryParams.specificationModel" placeholder="请选择规格型号" clearable>
          <el-option
              v-for="dict in specification_model"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="重量(KG)" prop="weightKg">
        <el-input
            v-model="queryParams.weightKg"
            placeholder="请输入重量(KG)"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交期" prop="deliveryDate">
        <el-date-picker clearable
                        v-model="queryParams.deliveryDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择交期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="是否优先生产" prop="isPriority">
        <el-select v-model="queryParams.isPriority" placeholder="请选择是否优先生产" clearable>
          <el-option
              v-for="dict in sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否加急" prop="isUrgent">
        <el-select v-model="queryParams.isUrgent" placeholder="请选择是否加急" clearable>
          <el-option
              v-for="dict in sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否已生产" prop="isProduced">
        <el-select v-model="queryParams.isProduced" placeholder="请选择是否已生产" clearable>
          <el-option
              v-for="dict in sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="生产日期" prop="productionDate">
        <el-date-picker clearable
                        v-model="queryParams.productionDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择生产日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="创建者" prop="createBy">
        <el-input
            v-model="queryParams.createBy"
            placeholder="请输入创建者"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
                        v-model="queryParams.createTime"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['mdm:productionOrder:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['mdm:productionOrder:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['mdm:productionOrder:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['mdm:productionOrder:export']"
        >导出
        </el-button>
      </el-col>

      <!-- 新增：导入按钮 -->
      <el-col :span="1.5">
        <el-button
            type="info"
            plain
            icon="Upload" v
            @click="handleImport"
            v-hasPermi="['mdm:productionOrder:import']"
        >导入
        </el-button>
      </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productionOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" align="center" prop="id"/>
      <el-table-column label="下单时间" align="center" prop="orderDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="产品类别" align="center" prop="productCategory">
        <template #default="scope">
          <dict-tag :options="product_category" :value="scope.row.productCategory"/>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="customerName">
        <template #default="scope">
          <dict-tag :options="customer_name" :value="scope.row.customerName"/>
        </template>
      </el-table-column>
      <el-table-column label="规格型号" align="center" prop="specificationModel">
        <template #default="scope">
          <dict-tag :options="specification_model" :value="scope.row.specificationModel"/>
        </template>
      </el-table-column>
      <el-table-column label="重量(KG)" align="center" prop="weightKg"/>
      <el-table-column label="交期" align="center" prop="deliveryDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否优先生产" align="center" prop="isPriority">
        <template #default="scope">
          <dict-tag :options="sys_yes_no" :value="scope.row.isPriority"/>
        </template>
      </el-table-column>
      <el-table-column label="是否加急" align="center" prop="isUrgent">
        <template #default="scope">
          <dict-tag :options="sys_yes_no" :value="scope.row.isUrgent"/>
        </template>
      </el-table-column>
      <el-table-column label="是否已生产" align="center" prop="isProduced">
        <template #default="scope">
          <dict-tag :options="sys_yes_no" :value="scope.row.isProduced"/>
        </template>
      </el-table-column>
      <el-table-column label="生产日期" align="center" prop="productionDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.productionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="创建者" align="center" prop="createBy"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['mdm:productionOrder:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['mdm:productionOrder:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改生产订单管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="productionOrderRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="下单时间" prop="orderDate">
          <el-date-picker clearable
                          v-model="form.orderDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择下单时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="产品类别" prop="productCategory">
          <el-select v-model="form.productCategory" placeholder="请选择产品类别">
            <el-option
                v-for="dict in product_category"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName">
          <el-select v-model="form.customerName" placeholder="请选择客户名称">
            <el-option
                v-for="dict in customer_name"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="规格型号" prop="specificationModel">
          <el-select v-model="form.specificationModel" placeholder="请选择规格型号">
            <el-option
                v-for="dict in specification_model"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="重量(KG)" prop="weightKg">
          <el-input v-model="form.weightKg" placeholder="请输入重量(KG)"/>
        </el-form-item>
        <el-form-item label="交期" prop="deliveryDate">
          <el-date-picker clearable
                          v-model="form.deliveryDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择交期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否优先生产" prop="isPriority">
          <el-radio-group v-model="form.isPriority">
            <el-radio
                v-for="dict in sys_yes_no"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否加急" prop="isUrgent">
          <el-radio-group v-model="form.isUrgent">
            <el-radio
                v-for="dict in sys_yes_no"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否已生产" prop="isProduced">
          <el-radio-group v-model="form.isProduced">
            <el-radio
                v-for="dict in sys_yes_no"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生产日期" prop="productionDate">
          <el-date-picker clearable
                          v-model="form.productionDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择生产日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 新增：数据导入对话框 -->
    <el-dialog title="数据导入" v-model="excelOpen" width="400px" append-to-body>
      <el-upload
          ref="uploadRef"
          class="upload-demo"
          :action="uploadExcelUrl"
          :headers="headers"
          :on-success="handleUploadSuccess"
          :on-error="handleUploadError"
          :before-upload="handleBeforeUpload"
          :limit="1"
          :auto-upload="false"
      >
        <template #trigger>
          <el-button type="primary">选择文件</el-button>
        </template>

        <el-button class="ml-3" type="success" @click="submitUpload">
          上传文件
        </el-button>

        <template #tip>
          <div class="el-upload__tip">
            文件格式：xlsx、xls，文件大小：1M以内
          </div>
        </template>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script setup name="ProductionOrder">
import {
  listProductionOrder,
  getProductionOrder,
  delProductionOrder,
  addProductionOrder,
  updateProductionOrder
} from "@/api/mdm/productionOrder";

/* 新增:引用 */
import {getToken} from "@/utils/auth.js";

const {proxy} = getCurrentInstance();
const {
  customer_name,
  product_category,
  specification_model,
  sys_yes_no
} = proxy.useDict('customer_name', 'product_category', 'specification_model', 'sys_yes_no');

const productionOrderList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    id: null,
    orderDate: null,
    productCategory: null,
    customerName: null,
    specificationModel: null,
    weightKg: null,
    deliveryDate: null,
    isPriority: null,
    isUrgent: null,
    isProduced: null,
    productionDate: null,
    remark: null,
    createBy: null,
    createTime: null,
    orderByColumn: "id", // 新增：按照id排序
    isAsc: "desc" // 新增：倒序
  },
  rules: {
    orderDate: [
      {required: true, message: "下单时间不能为空", trigger: "blur"}
    ],
    productCategory: [
      {required: true, message: "产品类别不能为空", trigger: "change"}
    ],
    customerName: [
      {required: true, message: "客户名称不能为空", trigger: "change"}
    ],
    specificationModel: [
      {required: true, message: "规格型号不能为空", trigger: "change"}
    ],
    weightKg: [
      {required: true, message: "重量(KG)不能为空", trigger: "blur"}
    ],
    deliveryDate: [
      {required: true, message: "交期不能为空", trigger: "blur"}
    ],
    isPriority: [
      {required: true, message: "是否优先生产不能为空", trigger: "change"}
    ],
    isUrgent: [
      {required: true, message: "是否加急不能为空", trigger: "change"}
    ],
    isProduced: [
      {required: true, message: "是否已生产不能为空", trigger: "change"}
    ],
    productionDate: [
      {required: true, message: "生产日期不能为空", trigger: "blur"}
    ],
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询生产订单管理列表 */
function getList() {
  loading.value = true;
  listProductionOrder(queryParams.value).then(response => {
    productionOrderList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    orderDate: null,
    productCategory: null,
    customerName: null,
    specificationModel: null,
    weightKg: null,
    deliveryDate: null,
    isPriority: null,
    isUrgent: null,
    isProduced: null,
    productionDate: null,
    remark: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  };
  proxy.resetForm("productionOrderRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加生产订单管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getProductionOrder(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改生产订单管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["productionOrderRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateProductionOrder(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addProductionOrder(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除生产订单管理编号为"' + _ids + '"的数据项？').then(function () {
    return delProductionOrder(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('mdm/productionOrder/export', {
    ...queryParams.value
  }, `productionOrder_${new Date().getTime()}.xlsx`)
}

/* 新增：打开数据导入对话框 */
const excelOpen = ref(false);

function handleImport() {
  excelOpen.value = true;
}

/* 新增：上传Excel文件 */
const uploadRef = ref({});

function submitUpload() {
  uploadRef.value.submit();
}

/* 新增：上传地址 */
const uploadExcelUrl = ref(process.env.VUE_APP_BASE_API + "/mdm/productionOrder/import");// 上传Excel文件地址
/* 新增：上传请求头 */
const headers = ref({Authorization: "Bearer " + getToken()});

/* 新增：上传成功回调 */
function handleUploadSuccess(res, file) {
  if (res.code === 200) {
    proxy.$modal.msgSuccess("上传Excel成功");
    excelOpen.value = false;
    getList();
  } else {
    proxy.$modal.msgError(res.msg);
  }
  uploadRef.value.clearFiles();
  proxy.$modal.closeLoading();
}

/* 新增：上传失败回调 */
function handleUploadError() {
  proxy.$modal.msgError("上传Excel失败");
  uploadRef.value.clearFiles();
  proxy.$modal.closeLoading();
}

/* 新增：上传前校验 */
const props = defineProps({
  modelValue: [String, Object, Array],
  // 大小限制(MB)
  fileSize: {
    type: Number,
    default: 1,
  },
  // 文件类型, 例如['xls', 'xlsx']
  fileType: {
    type: Array,
    default: () => ["xls", "xlsx"],
  },
});

/* 新增：上传前loading加载 */
function handleBeforeUpload(file) {
  let isExcel = false;
  if (props.fileType.length) {
    let fileExtension = "";
    if (file.name.lastIndexOf(".") > -1) {
      fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
    }
    isExcel = props.fileType.some(type => {
      if (file.type.indexOf(type) > -1) return true;
      if (fileExtension && fileExtension.indexOf(type) > -1) return true;
      return false;
    });
  }
  if (!isExcel) {
    proxy.$modal.msgError(`文件格式不正确，请上传${props.fileType.join("/")}格式文件!`);
    return false;
  }
  if (file.name.includes(',')) {
    proxy.$modal.msgError('文件名不正确，不能包含英文逗号!');
    return false;
  }
  if (props.fileSize) {
    const isLt = file.size / 1024 / 1024 < props.fileSize;
    if (!isLt) {
      proxy.$modal.msgError(`上传Excel大小不能超过 ${props.fileSize} MB!`);
      return false;
    }
  }
  proxy.$modal.loading("正在上传Excel，请稍候...");
}

getList();
</script>
