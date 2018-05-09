package com.sumory.gru.stat.servlet;

import com.alibaba.fastjson.JSONObject;
import com.sumory.gru.common.domain.StatObject;
import com.sumory.gru.common.utils.BitSetUtil;
import com.sumory.gru.stat.service.StatService;
import com.sumory.gru.stat.service.impl.StatServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class StatServlet extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(StatServlet.class);
    private StatService statService;
    protected String remoteAddressHeader = null;

    public StatServlet() {

    }

    @Override
    public void init() throws ServletException {
        statService = new StatServiceImpl();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();

        try{
            response.setCharacterEncoding("utf-8");
            if (contextPath == null) { // root context
                contextPath = "";
            }
            String path = requestURI.substring(contextPath.length() + servletPath.length());

            if ("/getGroupStat".equals(path)) {
                Long groupId = Long.parseLong(request.getParameter("groupId"));
                List<StatObject> stats = statService.getGroupStatObjectList(groupId);
                List<Integer> userIds = new ArrayList<Integer>();
                for (StatObject s : stats) {
                    if (s != null) {
                        s.setBitSet((BitSet) BitSetUtil.bytes2Object(s.getBitSetBytes()));
                        List<Integer> uIds = BitSetUtil.recoverFrom(s);
                        if (uIds != null && uIds.size() > 0)
                            userIds.addAll(uIds);
                    }
                }

                Map<String, Object> result = new HashMap<String, Object>();
                result.put("groupId", groupId);
                result.put("userIds", userIds);
                response.getWriter().write(JSONObject.toJSONString(result));
                return;
            }
            else {
                response.getWriter().write("wrong reqeust");
                return;
            }
        }catch(Exception e){
            logger.error("getGroupStat error", e);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("groupId", 0);
            result.put("userIds", Collections.emptyList());
            response.getWriter().write(JSONObject.toJSONString(result));
            return;
        }

    }

    protected String getRemoteAddress(HttpServletRequest request) {
        String remoteAddress = null;

        if (remoteAddressHeader != null) {
            remoteAddress = request.getHeader(remoteAddressHeader);
        }

        if (remoteAddress == null) {
            remoteAddress = request.getRemoteAddr();
        }

        return remoteAddress;
    }

}
