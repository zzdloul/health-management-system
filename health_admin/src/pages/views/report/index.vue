<script lang="ts" setup>

import { ref, onMounted } from "vue"
import { usePagination } from "@@/composables/usePagination"
import { Refresh, RefreshRight } from "@element-plus/icons-vue"
import * as echarts from "echarts"

defineOptions({
  // 命名当前组件
  name: "Report"
})

const loading = ref<boolean>(false)

const { paginationData } = usePagination()

// 图表实例
const pieChartRef = ref<HTMLElement | null>(null)
const barChartRef = ref<HTMLElement | null>(null)
const lineChartRef = ref<HTMLElement | null>(null)

const pieChart = ref<echarts.ECharts | null>(null)
const barChart = ref<echarts.ECharts | null>(null)
const lineChart = ref<echarts.ECharts | null>(null)

// 初始化饼图
const initPieChart = () => {
  if (pieChartRef.value) {
    pieChart.value = echarts.init(pieChartRef.value)
    const option = {
      title: {
        text: '套餐销售占比',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: '销售占比',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 30, name: '基础套餐' },
            { value: 25, name: '标准套餐' },
            { value: 20, name: '高级套餐' },
            { value: 15, name: 'VIP套餐' },
            { value: 10, name: '定制套餐' }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    }
    pieChart.value.setOption(option)
  }
}

// 初始化柱状图
const initBarChart = () => {
  if (barChartRef.value) {
    barChart.value = echarts.init(barChartRef.value)
    const option = {
      title: {
        text: '月度预约量',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        data: ['1月', '2月', '3月', '4月', '5月', '6月']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '预约量',
          type: 'bar',
          data: [120, 190, 300, 280, 400, 350]
        }
      ]
    }
    barChart.value.setOption(option)
  }
}

// 初始化折线图
const initLineChart = () => {
  if (lineChartRef.value) {
    lineChart.value = echarts.init(lineChartRef.value)
    const option = {
      title: {
        text: '会员增长趋势',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis'
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['1月', '2月', '3月', '4月', '5月', '6月']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '会员数',
          type: 'line',
          data: [1200, 1900, 3000, 4500, 6000, 7500]
        }
      ]
    }
    lineChart.value.setOption(option)
  }
}

// 刷新图表
const refreshCharts = () => {
  loading.value = true
  setTimeout(() => {
    initPieChart()
    initBarChart()
    initLineChart()
    loading.value = false
  }, 500)
}

// 监听窗口大小变化，调整图表大小
const handleResize = () => {
  pieChart.value?.resize()
  barChart.value?.resize()
  lineChart.value?.resize()
}

onMounted(() => {
  initPieChart()
  initBarChart()
  initLineChart()
  window.addEventListener('resize', handleResize)
})
</script>

<template>
  <div class="app-container">
    <el-alert title="数据统计" type="success" description="健康管理系统数据统计与分析" show-icon />
    <el-card v-loading="loading" shadow="never" class="chart-container">
      <div class="toolbar-wrapper">
        <div>
          <el-button type="primary" :icon="RefreshRight" @click="refreshCharts">
            刷新数据
          </el-button>
        </div>
      </div>
      <div class="charts-grid">
        <div class="chart-item">
          <div ref="pieChartRef" class="chart"></div>
        </div>
        <div class="chart-item">
          <div ref="barChartRef" class="chart"></div>
        </div>
        <div class="chart-item full-width">
          <div ref="lineChartRef" class="chart"></div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<style lang="scss" scoped>
.el-alert {
  margin-bottom: 20px;
}

.chart-container {
  margin-bottom: 20px;
}

.toolbar-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.charts-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 20px;
}

.chart-item {
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 16px;
}

.chart-item.full-width {
  grid-column: 1 / -1;
}

.chart {
  width: 100%;
  height: 400px;
}

@media (max-width: 768px) {
  .charts-grid {
    grid-template-columns: 1fr;
  }
  
  .chart-item.full-width {
    grid-column: 1;
  }
}
</style>