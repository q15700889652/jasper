package jasper.scorrtUtil.JasperScorrt.param;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public interface ParamLoding {
	
	HashMap<String, Object> paramLoding (HttpServletRequest request);
}
