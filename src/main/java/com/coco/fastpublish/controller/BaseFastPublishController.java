package com.coco.fastpublish.controller;

import com.coco.fastpublish.entity.SqlFragment;
import com.coco.fastpublish.service.SqlFragmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BaseFastPublishController {

    @Autowired
    private SqlFragmentService sqlFragmentService;

    @RequestMapping("/fragment/add")
    @ResponseBody
    public Map create(SqlFragment fragment){
        SqlFragment frag = sqlFragmentService.getByCode(fragment.getCode());
        Map map = new HashMap();
        if (frag != null) {
            map.put("success", false);
            map.put("msg", "code以被使用");
            return map;
        }
        map.put("success", sqlFragmentService.createRetBool(fragment));
        map.put("msg", "");
        return map;
    }

    @RequestMapping("/fragment/update-by-id")
    @ResponseBody
    public Map updateById(SqlFragment fragment){
        Map map = new HashMap();
        if(null == fragment || null == fragment.getId()){
            map.put("msg", "id不能为空");
            map.put("success", false);
            return map;
        }
        map.put("success", sqlFragmentService.updateByIdRetBool(fragment));
        map.put("msg", "");
        return map;
    }

    @RequestMapping("/fragment/update-by-code")
    @ResponseBody
    public Map updateByCode(SqlFragment fragment) {
        Map map = new HashMap();
        if (null == fragment || null == fragment.getCode()) {
            map.put("msg", "code不能为空");
            map.put("success", false);
            return map;
        }
        map.put("success", sqlFragmentService.updateByCodeRetBool(fragment));
        map.put("msg", "");
        return map;
    }

    @RequestMapping("/fragment/delete-by-id")
    @ResponseBody
    public Map deleteById(SqlFragment fragment){
        Map map = new HashMap();
        if(null == fragment || null == fragment.getId()){
            map.put("msg", "id不能为空");
            map.put("success", false);
            return map;
        }
        map.put("success", sqlFragmentService.deleteRetBool(fragment));
        return map;
    }

    @RequestMapping("/fragment/list")
    @ResponseBody
    public List<SqlFragment> list(){
        return sqlFragmentService.list();
    }

    @RequestMapping("/fragment/list-page")
    @ResponseBody
    public List<SqlFragment> listPage(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo, @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        return sqlFragmentService.list(pageNo,pageSize);
    }

    @RequestMapping("/fragment/get-by-id")
    @ResponseBody
    public SqlFragment getById(Long id){
        return sqlFragmentService.getById(id);
    }

    @RequestMapping("/fragment/get-by-code")
    @ResponseBody
    public SqlFragment getByCode(String code){
        return sqlFragmentService.getByCode(code);
    }

}
