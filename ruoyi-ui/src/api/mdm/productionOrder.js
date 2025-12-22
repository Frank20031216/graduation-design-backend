import request from '@/utils/request'

// 查询生产订单管理列表
export function listProductionOrder(query) {
  return request({
    url: '/mdm/productionOrder/list',
    method: 'get',
    params: query
  })
}

// 查询生产订单管理详细
export function getProductionOrder(id) {
  return request({
    url: '/mdm/productionOrder/' + id,
    method: 'get'
  })
}

// 新增生产订单管理
export function addProductionOrder(data) {
  return request({
    url: '/mdm/productionOrder',
    method: 'post',
    data: data
  })
}

// 修改生产订单管理
export function updateProductionOrder(data) {
  return request({
    url: '/mdm/productionOrder',
    method: 'put',
    data: data
  })
}

// 删除生产订单管理
export function delProductionOrder(id) {
  return request({
    url: '/mdm/productionOrder/' + id,
    method: 'delete'
  })
}
