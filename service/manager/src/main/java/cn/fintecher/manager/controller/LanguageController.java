package cn.fintecher.manager.controller;

import cn.fintecher.manager.filter.GenerateJsLocale;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @ClassName LanguageController
 * @Description 多语言
 * @Author coder_bao
 * @Date 2018/8/30 16:37
 */
@RestController
@RequestMapping("/api/language")
@Api(value = "切换语言", description = "切换语言")
public class LanguageController {
    private static final String ENTITY_NAME = "change_language";
    @Autowired
    private LocaleMessage localeMessage;

    @ApiOperation(value = "切换语言", httpMethod = "POST", notes = "切换语言")
    @PostMapping("/change")
    public ResponseEntity changeLanguage(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "langType", defaultValue = "zh") String langType){
        Locale locale;
        if ("zh".equals(langType)) {
            locale = new Locale("zh", "CN");
        } else if ("en".equals(langType)) {
            locale = new Locale("en", "");
        } else {
            locale = LocaleContextHolder.getLocale();
        }
        (new CookieLocaleResolver()).setLocale(request, response, locale);
        localeMessage.changeRedisLocale(locale);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeMessage.get("message.system.successMessage"), ENTITY_NAME)).body(null);

    }
    @RequestMapping(value = "/locale/language")
    @ApiOperation(value = "获取多语言资源文件", httpMethod = "GET", notes = "获取多语言资源文件")
    public void getLanguage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Locale locale = LocaleMessage.getLocale();
        response.setContentType("application/x-javascript;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(GenerateJsLocale._LOCALEMAP.get(locale.toString()));
            writer.flush();
        } catch (IOException e) {
            throw e;
        }
    }
}
