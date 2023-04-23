package com.example.jiuYe2.test;

import redis.clients.jedis.Jedis;

public class JedisTest {

    public static void main(String[] args) {

        // 选择端口为6379的第1个数据库，默认为6379第0个。
        Jedis jedis = new Jedis("redis://localhost:6379/0");

//        // 删除库内所有数据
//        jedis.flushDB();

//        jedis.set("hello", "world");
//        System.out.println("hello: " + jedis.get("hello"));
//        jedis.rename("hello", "hello2");
//        jedis.del("hello2");
//        // 带过期时间的存入，单位为秒，可用于验证码
//        jedis.setex("hello3", 10, "world3");

//        jedis.set("count", "100");
//        // 加1
//        jedis.incr("count");
//        System.out.println("count: " + jedis.get("count"));
//        // 加参数值
//        jedis.incrBy("count", 10);
//        System.out.println("count: " + jedis.get("count"));
//        // 减1
//        jedis.decr("count");
//        System.out.println("count: " + jedis.get("count"));
//        // 减参数值
//        jedis.decrBy("count", 10);
//        System.out.println("count: " + jedis.get("count"));

//        // 返回匹配的键，可用正则表达式
//        System.out.println(jedis.keys("*"));

//        // 可存列表list，l开头方法为列表方法
//        String listName = "list";
//        for (int i = 0; i < 5; i++) {
//            //lpush为头插法，lpop弹出头元素，可当栈使用
//            jedis.lpush(listName, "item" + i);
//        }
//        jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "item3", "item3.5");
//        jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "item3", "item2.5");
//        System.out.println(jedis.lrange(listName, 0, 10));

//        // 可存哈希表hashMap，h开头方法为哈希表方法
//        String user = "user";
//        jedis.hset(user, "name", "jim");
//        jedis.hset(user, "age", "12");
//        // 如果已存在，则不存入
//        jedis.hsetnx(user, "address", "阿斯顿");
//        System.out.println(jedis.hgetAll(user));
//        jedis.hdel("age");
//        System.out.println(jedis.hexists(user, "name"));

//        // 可存集合set，s开头方法为集合方法
//        String set1 = "set1";
//        String set2 = "set2";
//        for (int i = 0; i < 5; i++) {
//            jedis.sadd(set1, String.valueOf(i));
//            jedis.sadd(set2, String.valueOf(i * i));
//        }
//        System.out.println(jedis.smembers(set1));
//        System.out.println(jedis.smembers(set2));
//        // 集合求并集
//        System.out.println(jedis.sunion(set1, set2));
//        // 集合求交集
//        System.out.println(jedis.sinter(set1, set2));
//        // 集合求差集，输出set1有set2没有的元素
//        System.out.println(jedis.sdiff(set1, set2));
//        jedis.srem(set1, "3");
//        System.out.println(jedis.sismember(set1, "3"));
//        // 把set1中的2移动到set2的末尾
//        jedis.smove(set1, set2, "2");
//        //set1中元素总数
//        jedis.scard(set1);
//        //在set1中随机输出参数个元素，抽奖
//        System.out.println(jedis.srandmember(set1, 2));

//        // 可存有序集合zset，类似堆排序的堆，z开头方法为有序集合方法。
//        String rank = "rank";
//        jedis.zadd(rank, 40, "jia");
//        jedis.zadd(rank, 80, "yi");
//        jedis.zadd(rank, 60, "bing");
//        jedis.zadd(rank, 90, "ding");
//        // 处于区间内的元素总数，直接求总数用zcard
//        System.out.println(jedis.zcount(rank, 60, 100));
//        // 通过键返回值
//        System.out.println(jedis.zscore(rank, "bing"));
//        // 通过键对元素的值进行计算，若键不存在，则将其插入设初值为0，再进行计算。
//        jedis.zincrby(rank, 60, "jia");
//        // 按从小到大排序后输出从0到10位，从大到小用zrevrange，也可用于字母
//        System.out.println(jedis.zrange(rank, 0, 10));
//        // 输出元素的从小到大的排名，从大到小用zrevrank
//        System.out.println(jedis.zrank(rank, "yi"));
//        // zset遍历
//        for (Tuple tuple : jedis.zrangeByScoreWithScores(rank, 60, 100)) {
//            System.out.println(tuple.getElement() + ": " + tuple.getScore());
//        }
//        String alphaRank = "alphaRank";
//        jedis.zadd(alphaRank, 0, "a");
//        jedis.zadd(alphaRank, 0, "b");
//        jedis.zadd(alphaRank, 0, "c");
//        jedis.zadd(alphaRank, 0, "d");
//        jedis.zadd(alphaRank, 0, "e");
//        // 按字母顺序排序后处于区间内的元素总数，-为负无穷，+为正无穷，[为闭区间，(为开区间。
//        System.out.println(jedis.zlexcount(alphaRank, "(a", "[d"));
//        // 移除区间内的字母元素
//        jedis.zremrangeByLex(alphaRank, "(c", "+");

//        // 连接池，默认可分配8个jedis对象。
//        JedisPool pool = new JedisPool();
//        for (int i = 0; i < 10; i++) {
//            Jedis j = pool.getResource();
//            System.out.println(j.get("hello"));
//            // 用完需释放，否则将会一直占用jedis对象，导致其他申请无法完成。
//            j.close();
//        }

//        User user = new User();
//        user.setName("qwerty");
//        user.setPassword("asdfg");
//        user.setSalt("zxcvb");
//        // 通过json串序列化对象并存入redis或取出。
//        jedis.set("user1", JSONObject.toJSONString(user));
//        User user2 = JSON.parseObject(jedis.get("user1"), User.class);

    }
}
