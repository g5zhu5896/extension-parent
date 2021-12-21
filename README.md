# 主要是提供枚举和字典mvc三层bean的转换,在一定层度上简化了开发。
# 比如集成了查询实体关联字典表不用写join就可以自己关联绑定,而且默认一次request相同字典会进行缓存不会重复查库,这个可以定制策略如缓存到redis。
详见 https://blog.csdn.net/g5zhu5896/article/details/122022305
