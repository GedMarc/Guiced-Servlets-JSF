package com.guicedee.guicedservlets.jsf.implementations;

import com.guicedee.guicedinjection.interfaces.IGuicePostStartup;
import com.guicedee.logger.LogFactory;
import com.guicedee.jpms.schemas.facesconfig.FacesConfigType;

import java.util.logging.Logger;

public class FacesConfigMergerPostStartup implements IGuicePostStartup<FacesConfigMergerPostStartup> {

	private static final Logger log = LogFactory.getLog("FacesConfiguratorMergerPostStartup");

	public static String facesConfigMergedLocation;
	public static String facesConfigMergedFilename;

	public static FacesConfigType allTypes;

	@Override
	public void postLoad() {
		log.config("Building Merged Faces Configs");
	//	FacesConfigType overall = new FacesConfigType();

	//	StringBuilder locations = new StringBuilder();

	/*	for (Map.Entry<URI, FacesConfigType> entry : FacesConfigFileHandler.getFacesConfigFiles()
																		   .entrySet()) {
			URI key = entry.getKey();
			FacesConfigType value = entry.getValue();
			overall.getApplicationOrOrderingOrAbsoluteOrdering()
				   .addAll(value.getApplicationOrOrderingOrAbsoluteOrdering());
		}*/
	//	allTypes = overall;
/*		try {
			File f = File.createTempFile("faces-config",".xml");
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				 FileWriter fw = new FileWriter(f,UTF_8)){
				FacesConfigByteArrayConsumer.facesContext.createMarshaller().marshal(overall,baos);
				String output = new String(baos.toByteArray());
				fw.write(output);
				facesConfigMergedLocation = f.getParentFile().getPath();
				facesConfigMergedFilename = "/" + f.getName();
				//GuicedUndertowResourceManager.addSearchLocation(facesConfigMergedLocation);
				//GuicedUndertowResourceManager.addDeniedResourceContains("faces-config.xml");
				//GuicedUndertowResourceManager.addResourceReplacement("/WEB-INF/faces-config.xml",f.getCanonicalPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/

	}

	@Override
	public Integer sortOrder() {
		return Integer.MIN_VALUE + 15;
	}


}
