package com.coco.fastpublish.controller;

import com.coco.fastpublish.service.SqlExecuter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FastPublicController {

    @Autowired
    private SqlExecuter sqlExecuter;

    @PostMapping("/fast-public/test")
    @ResponseBody
    public List<Map> testFastPublic(@RequestBody Map params, @RequestParam("sql") String sqlFragment){
        return sqlExecuter.executeSelect(params, sqlFragment);
    }

    @GetMapping("/fast-public/test")
    @ResponseBody
    public List<Map> testFastPublicSimpleParam(@RequestParam Map params, @RequestParam("sql") String sqlFragment){
        params.remove("sql");
        return sqlExecuter.executeSelect(params, sqlFragment);
    }

    @PostMapping("/fast-public/execute")
    @ResponseBody
    public List<Map> executeFastPublic(@RequestBody Map params, @RequestParam("code") String code){
        return sqlExecuter.executeSelect(code,params);
    }

    @GetMapping("/fast-public/execute")
    @ResponseBody
    public List<Map> executeFastPublicSimpleParam(@RequestParam Map params, @RequestParam("code") String code){
        params.remove("code");
        return sqlExecuter.executeSelect(code,params);
    }

    @PostMapping("/fast-public/execute/ret-num")
    @ResponseBody
    public Map executeRetNum(@RequestBody Map params, @RequestParam("code") String code){
        int execute = sqlExecuter.execute(code, params);
        Map map = new HashMap();
        map.put("effectRow", execute);
        map.put("success", execute>0);
        return map;
    }

    @GetMapping("/fast-public/execute/ret-num")
    @ResponseBody
    public Map executeRetNumSimpleParam(@RequestParam Map params, @RequestParam("code") String code){
        params.remove("code");
        int execute = sqlExecuter.execute(code, params);
        Map map = new HashMap();
        map.put("effectRow", execute);
        map.put("success", execute>0);
        return map;
    }

    @RequestMapping("/fast-public/delete")
    @ResponseBody
    public Map deleteFastPublic(String code){
        Map map = new HashMap();
        map.put("success",sqlExecuter.deleteSqlFragment(code));
        return map;
    }

    @RequestMapping("/fast-public/update")
    @ResponseBody
    public Map updateFastPublic(String code){
        Map map = new HashMap();
        map.put("success",sqlExecuter.updateSqlFragment(code));
        return map;
    }

}
