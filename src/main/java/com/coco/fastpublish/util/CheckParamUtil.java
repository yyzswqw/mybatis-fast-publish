package com.coco.fastpublish.util;

import cn.hutool.core.bean.BeanUtil;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CheckParamUtil {

    /**
     * 检查参数
     *
     * @param baseTrie 校验规则
     * @param trie     数据
     * @return
     */
    public static StringBuilder checkParam(Trie<String, Object> baseTrie, Trie<String, Object> trie) {
        StringBuilder sb = new StringBuilder();
        if (baseTrie == null || baseTrie.isEmpty()) {
            return sb;
        }
        if (trie == null) {
            trie = new PatriciaTrie();
        }
        final Iterator<String> iterator = baseTrie.keySet().iterator();
        while (iterator.hasNext()) {
            String path = null;
            String nextKey = iterator.next();
            Object o = baseTrie.get(nextKey);
            if (o instanceof Collection) {
                String realKey = nextKey;
                path = realKey.replace("-", "");
                Collection o1 = null;
                try {
                    o1 = (Collection) trie.get(realKey.replace("-", ""));
                } catch (Exception e) {
                    sb.append(path + ":format incorrect\n");
                    continue;
                }
                boolean isNotMust = realKey.startsWith("-");
                Map<String, Object> baseMap = null;
                try {
                    baseMap = (Map<String, Object>) (((Collection) baseTrie.get(nextKey)).iterator().next());
                } catch (Exception e) {
//                    普通数组，检查是否必须传即可
                    if (!realKey.startsWith("-") && ((Collection) baseTrie.get(nextKey)).size() <= 0) {
                        sb.append(path + ":is null\n");
                        continue;
                    }
                }
                if (!isNotMust && (o1 == null || o1.isEmpty())) {
                    sb.append(path + ":is null\n");
                    break;
                } else if (isNotMust && o1 == null) {
                    continue;
                }
                sb.append(checkCollection(baseMap, o1, realKey));
            } else if (o instanceof Map) {
                String realKey = nextKey.replace("-", "");
                Map<String, Object> valueMap = null;
                try {
                    valueMap = (Map<String, Object>) trie.get(realKey);
                } catch (Exception e) {
                    sb.append(path + ":format incorrect\n");
                    continue;
                }
                boolean isNotMust = nextKey.startsWith("-");
                path = realKey;
                if (!isNotMust && valueMap == null) {
                    sb.append(path + ":is null\n");
                    break;
                } else if (isNotMust && valueMap == null) {
                    continue;
                }
                checkMap((Map<String, Object>) o, valueMap, path);
            } else {
                path = nextKey;
                Object nextValue = baseTrie.get(nextKey);
                if (new Integer(1).equals(nextValue)) {
                    Object value = trie.get(nextKey);
                    if (value == null) {
                        sb.append(path + ":is null\n");
                    }
                } else {
                    sb.append(path + ":format incorrect\n");
                }
            }
        }
        return sb;
    }

    /**
     * 检查集合中的参数
     *
     * @param baseMap 校验规则
     * @param coll    数据
     * @param path    key路径
     * @return
     */
    private static StringBuilder checkCollection(Map<String, Object> baseMap, Collection coll, String path) {
        StringBuilder sb = new StringBuilder();
        if (coll == null || baseMap == null) {
            return sb;
        }
        int i = 0;
        Iterator<String> iterator = null;
        for (Object o2 : coll) {
            iterator = baseMap.keySet().iterator();
            String key = iterator.next();
            Map<String, Object> value = null;
            try {
                value = (Map<String, Object>) baseMap.get(key.replace("-", ""));
            } catch (Exception e) {
//                判断是否为普通集合，普通集合只需要检查是否必传
                Collection co = null;
                try {
                    co = (Collection) o2;
                    Map<String, Object> checkMap = (Map<String, Object>) co.iterator().next();
                    sb.append(checkCollection(baseMap, (Collection) o2, path));
                } catch (Exception e1) {
//                    普通集合不能转Map
                    if (!key.startsWith("-") && co != null && co.isEmpty()) {
                        sb.append(path + ":is null\n");
                        continue;
                    }
                }
            }
            if (o2 instanceof Map) {
                sb.append(checkMap(baseMap, (Map) o2, path + "[" + i + "]"));
            }
            i++;
        }
        return sb;
    }

    /**
     * 检查Map中的参数
     *
     * @param baseMap 校验规则
     * @param map     数据
     * @param path    key路径
     * @return
     */
    private static StringBuilder checkMap(Map<String, Object> baseMap, Map<String, Object> map, String path) {
        StringBuilder sb = new StringBuilder();
        if (baseMap == null) {
            return sb;
        }
        Iterator iterator = baseMap.keySet().iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            String pa = path;
            pa = (pa == null) ? key.toString() : pa.concat("->").concat(key.toString());
            if (new Integer(1).equals(baseMap.get(key))) {
                if (map == null) {
                    sb.append(pa + ":is null\n");
                    break;
                }
                Object value = map.get(key);
                if (value == null) {
                    sb.append(pa + ":is null\n");
                } else if (baseMap.get(key) instanceof Map) {
                    if (!(value instanceof Map)) {
                        sb.append(pa + ":format incorrect\n");
                        continue;
                    }
                    checkMap((Map) baseMap.get(key), (Map) value, pa);
                } else if (baseMap.get(key) instanceof Collection) {
                    if (!(value instanceof Collection)) {
                        sb.append(pa + ":format incorrect\n");
                        continue;
                    }
                    checkCollection((Map) ((Collection) baseMap.get(key)).iterator().next(), (Collection) value, pa);
                }
            }
        }
        return sb;
    }

    /**
     * 将对象转Map
     *
     * @param o
     * @return
     */
    public static Map objectToMap(Object o) {
        Map map = new HashMap();
        if (o == null) {
            return map;
        }
        if (o instanceof Map) {
            return (Map) o;
        }
        return BeanUtil.beanToMap(o);
    }

}
