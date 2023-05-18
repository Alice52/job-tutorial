## xxljob

1. overview

   - 定义: **轻量**级**分布式高可用**任务**调度**平台
   - 设计目标: **动态** & 易扩展 & 关注业务 & 简单 & 易用
   - 核心架构: 调度中心 | 执行中心
   - 一致性: 调度中心通过 DB 锁保证集群分布式调度的一致性(一次任务调度只会触发一次执行)
   - 补充说明: 没有使用 quartz, 自己实现的时间轮

2. execute flow

   ![avatar](/static/image/xxljob-flow.png)

3. v2.1.0 架构图: `调度系统与任务解耦, 提高了系统可用性和稳定性, 同时调度系统性能不再受限于任务模块`

   ![avatar](/static/image/xxljob-artch-2.4.0.png)

   - 调度模块: 独立部署 + 只负责任务调度 + 可视化且动态的管理
   - 执行模块: 集群 & 内嵌在应用内(netty) + 复杂业务执行

4. source code

   - 注册中心&发现: 执行器 & 调度器
   - 任务触发: 调度器
   - 任务执行: 执行器
   - 任务回调: 执行器
   - xxl-rpc
     1. 调度中心提供调用代理的功能
     2. 执行器提供远程服务的功能