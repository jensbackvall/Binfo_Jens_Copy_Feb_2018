package dk.binfo.configurations;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Whenever a user attempts to access a page that is restricted to roles they do not have,
 * the application will return a status code of 403, which means Access Denied.<p></p>
 * Following Roles
 * <ul>
 * <li>Admin
 * <li>User
 * </ul><p></p>
 *
 * By default, Spring Security has an ExceptionTranslationFilter defined
 * which handles exceptions of type AuthenticationException and AccessDeniedException.
 * The latter is done through a property called accessDeniedHandler,
 * which uses the AccessDeniedHandlerImpl class.
 * <p>
 * In order to customize this behavior to use our own page that we created (see <a href="/accessDenied">AccessDenied page</a>),
 * we need to override the properties of the ExceptionTranslationFilter class.
 * This can be done through either Java configuration or XML configuration.
 *<br>
 * Using Java, I customized the 403 error handling process by using the accessDeniedHandler()
 * methods while configuring the HttpSecurity element.
 *<br>
 * Using an access denied handler instead of a page has the advantage
 * that we can define custom logic to be executed before redirecting to the 403 page.
 * For this, we created a class that implements the AccessDeniedHandler interface
 * and overrides the handle() method.
 *
 * <p>
 * More implamentations:
 * <ul>
 *     <li>Add the logs to the database, with a page for admins to view logs</li>
 *     <li>Add log file if web application running on dedicated server</li>
 * </ul>
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     *public final class LoggerFactory
     * extends Object
     * <br>The LoggerFactory is a utility class producing Loggers for various logging APIs, most notably for log4j, logback and JDK 1.4 logging. Other implementations such as NOPLogger and SimpleLogger are also supported.
     * LoggerFactory is essentially a wrapper around an ILoggerFactory instance bound with LoggerFactory at compile time.<p></p>
     *
     * Please note that all methods in LoggerFactory are static.
     * <br>
     *     Author: Alexander Dorokhine, Robert Elliot, Ceki Gülcü
     */
    public static final Logger LOG = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    /**
     *<br>
     * The custom logic logs a warning message for every access denied attempt
     * containing the user that made the attempt and the protected URL they were trying to access:
     * <p>
     * 2017-11-30 10:42:47.236  WARN 5232 --- [nio-8080-exec-6] d.b.c.CustomAccessDeniedHandler
     * <br>User: user@test.dk[user] attempted to access the protected URL: /apartment<br>
     * This user tried to access /apartment which requied admin rank.
     *
     * @param request Extends the ServletRequest interface to provide request information for HTTP servlets.
     * @param response Extends the ServletResponse interface to provide HTTP-specific functionality in sending a response. For example, it has methods to access HTTP headers and cookies.
     * @param exc Checked exception thrown when a operation is denied, typically due to a file permission or other access check.
     *
     */
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            LOG.warn("User: " + auth.getName() +  auth.getAuthorities() + " attempted to access the protected URL: " + request.getRequestURI());
        }
        response.sendRedirect(request.getContextPath() + "/accessDenied");
    }

}
