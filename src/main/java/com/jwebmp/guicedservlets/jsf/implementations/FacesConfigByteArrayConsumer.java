package com.guicedee.guicedservlets.jsf.implementations;

import io.github.classgraph.Resource;
import io.github.classgraph.ResourceList;
import com.guicedee.logger.LogFactory;
import com.guicedee.jpms.schemas.facesconfig.FacesConfigType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A consumer that reads persistence.xml files into PersistenceUnit objects
 */
public class FacesConfigByteArrayConsumer
		implements ResourceList.ByteArrayConsumer
{
	/**
	 * The logger
	 */
	private static final Logger log = LogFactory.getLog("FacesConfigByteArrayConsumer");

	public static JAXBContext facesContext;
	static {
		try {
			facesContext = JAXBContext.newInstance(FacesConfigType.class);
		} catch (JAXBException e) {
			facesContext = null;
			log.log(Level.SEVERE, "Faces Context Cannot be Built", e);
		}
	}
	/**
	 * Method accept ...
	 *
	 * @param resource
	 * 		of type Resource
	 * @param byteArray
	 * 		of type byte[]
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void accept(Resource resource, byte[] byteArray)
	{
		try (resource) {
			log.config("Loading faces-config - " + resource.toString());
			if (!new String(byteArray).contains("version=\"2.3\"")) {
				log.warning("Bypassing not faces 2.3 - " + resource.toString());
				return;
			}

			try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray)) {
				JAXBElement<FacesConfigType> o = (JAXBElement<FacesConfigType>) facesContext.createUnmarshaller()
																							.unmarshal(bais);
				FacesConfigType fct = o.getValue();
				FacesConfigFileHandler.getFacesConfigFiles()
									  .put(resource.getClasspathElementURI(), fct);
			} catch (Exception e) {
				log.log(Level.WARNING, "Unable to process faces-config file", e);
			}
		}
	}

}
