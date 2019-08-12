
<%@ page language="java"  pageEncoding="utf-8" %><%@ page import="com.clbee.pbcms.util.StringUtil,
    org.springframework.context.MessageSource,
    org.springframework.web.context.WebApplicationContext,
    org.springframework.web.context.support.WebApplicationContextUtils,
    java.io.File,
    java.io.FileInputStream,
    java.io.OutputStream,
    java.io.PrintStream,
    java.lang.management.ManagementFactory,
    java.lang.management.MemoryMXBean"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.Locale" %>
<%
    response.setHeader("Cache-Control","private, max-age=1");
    response.setHeader("Pragma","token");

    String oldFileName    = (String)request.getAttribute("orgFileName");
    String savedFileName  = (String)request.getAttribute("saveFileName");
    String filepath  = (String)request.getAttribute("filepath");
 
    System.out.println("[oldFileName] : " + oldFileName);
    System.out.println("[savedFileName] : " + savedFileName);
    System.out.println("[filepath] : " + filepath);

    //--------------------------------------------------------------------------

    out.clear();
    PrintStream printstream = new PrintStream(response.getOutputStream(), true);
    FileInputStream in = null;
    OutputStream    os = null;
    try {
        File file = new File(filepath + savedFileName );
        in = new FileInputStream(file);

        String strClient=request.getHeader("User-Agent");

        response.reset();
        response.setContentType("application/unknown");
        response.setHeader("Accept-Ranges", "bytes");

        //UTF-8 프로젝트에서 한글처리를 위해 URLEncoder를 이용하여 인코딩해줘야 한글이 제대로 보여진다.
        //response.setHeader("Content-Disposition", "attachment; filename=\""+ URLEncoder.encode(oldFileName, "UTF-8") +";\"");

        oldFileName = oldFileName.replaceAll(" ", "_");

        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(oldFileName, "UTF-8") + "\";");
        response.setHeader("Content-Length", ""+file.length() );

        os = response.getOutputStream();

        byte b[] = new byte[2048];
        int len = 0;
        while( (len = in.read(b)) > 0 )
            os.write(b, 0, len);

    } catch(Exception e) {
        System.out.println(e.toString());
        //window.history.back();
        //String alertStr = MakeJScript.alert("파일 다운로드 중 오류가 발생하였습니다.");
       // String alertStr = "파일 다운로드 중 오류가 발생하였습니다.";
       // response.getWriter(alertStr);
    } finally {
        if(in !=null) try{in.close();} catch(Exception e){}
        if(os !=null) try{os.close();}  catch(Exception e){}
    }


    MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    System.out.println("=================================== "+ memoryBean.getHeapMemoryUsage() );
%>