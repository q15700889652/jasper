package jasper.scorrtUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.type.ImageTypeEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.OnErrorTypeEnum;
import net.sf.jasperreports.engine.util.JRImageLoader;
import net.sf.jasperreports.engine.util.JRTypeSniffer;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.renderers.DataRenderable;
import net.sf.jasperreports.renderers.Renderable;
import net.sf.jasperreports.renderers.ResourceRenderer;
import net.sf.jasperreports.renderers.util.RendererUtil;
import net.sf.jasperreports.repo.RepositoryUtil;

public class sessiongettest extends BaseHttpServlet{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;


	/**
	 *
	 */
	public static final String IMAGE_NAME_REQUEST_PARAMETER = "image";

			
	@Override
	public void service(
		HttpServletRequest request,
		HttpServletResponse response
		) throws IOException, ServletException
	{
		byte[] imageData = null;
		String imageMimeType = null;

		String imageName = request.getParameter(IMAGE_NAME_REQUEST_PARAMETER);
		if ("px".equals(imageName))
		{
			try
			{
				imageData = RepositoryUtil.getInstance(getJasperReportsContext()).getBytesFromLocation(JRImageLoader.PIXEL_IMAGE_RESOURCE);
				imageMimeType = ImageTypeEnum.GIF.getMimeType();
			}
			catch (JRException e)
			{
				throw new ServletException(e);
			}
		}
		else
		{	
			System.out.println(Cache.map.get("sessiontest")+"-------+++++");
			System.out.println("request:"+request.getParameterMap().toString());
			//session 丢失问题
			request.getSession().setAttribute(
					ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
					(JasperPrint)Cache.map.get(request.getParameterMap().toString()));
			List<JasperPrint> jasperPrintList = BaseHttpServlet.getJasperPrintList(request);
			
			if (jasperPrintList == null)
			{
				throw new ServletException("No JasperPrint documents found on the HTTP session.");
			}
			
			JRPrintImage image = HtmlExporter.getImage(jasperPrintList, imageName);
			
			Renderable renderer = image.getRenderer();
			
			Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
			Color backcolor = ModeEnum.OPAQUE == image.getModeValue() ? image.getBackcolor() : null;
			
			RendererUtil rendererUtil = RendererUtil.getInstance(getJasperReportsContext());
			
			try
			{
				imageData = process(renderer, dimension, backcolor);
			}
			catch (Exception e)
			{
				try
				{
					Renderable onErrorRenderer = rendererUtil.handleImageError(e, image.getOnErrorTypeValue());
					if (onErrorRenderer != null)
					{
						imageData = process(onErrorRenderer, dimension, backcolor);
					}
				}
				catch (Exception ex)
				{
					throw new ServletException(ex);
				}
			}
			
			imageMimeType = 
				rendererUtil.isSvgData(imageData)
				? RendererUtil.SVG_MIME_TYPE
				: JRTypeSniffer.getImageTypeValue(imageData).getMimeType();
		}

		if (imageData != null && imageData.length > 0)
		{
			if (imageMimeType != null) 
			{
				response.setHeader("Content-Type", imageMimeType);
			}
			response.setContentLength(imageData.length);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(imageData, 0, imageData.length);
			outputStream.flush();
			outputStream.close();
		}
	}

	
	protected byte[] process(
		Renderable renderer,
		Dimension dimension,
		Color backcolor
		) throws JRException
	{
		RendererUtil rendererUtil = RendererUtil.getInstance(getJasperReportsContext());
		
		if (renderer instanceof ResourceRenderer)
		{
			renderer = //hard to use a cache here and it would be just for some icon type of images, if any 
					rendererUtil.getNonLazyRenderable(
					((ResourceRenderer)renderer).getResourceLocation(), 
					OnErrorTypeEnum.ERROR
					);
		}

		DataRenderable dataRenderer = 
				rendererUtil.getDataRenderable(
				renderer,
				dimension,
				backcolor
				);
		
		return dataRenderer.getData(getJasperReportsContext());
	}
}
