package com.guicedee.guicedservlets.jsf.faces;

import com.guicedee.guicedservlets.jsf.FacesConfiguration;
import com.guicedee.logger.LogFactory;
import io.github.classgraph.Resource;
import io.github.classgraph.ResourceList;

import java.util.logging.Logger;

/**
 * A consumer that reads FacesConfig.xml files into PersistenceUnit objects
 */
public class FacesConfigByteArrayConsumer
		implements ResourceList.ByteArrayConsumer
{
	/**
	 * The logger
	 */
	private static final Logger log = LogFactory.getLog("FacesConfigByteArrayConsumer");

	/**
	 * Method accept ...
	 *
	 * @param resource
	 * 		of type Resource
	 * @param byteArray
	 * 		of type byte[]
	 */
	@Override
	public void accept(Resource resource, byte[] byteArray)
	{
		log.config("Loading faces-config file - " + resource.getURL());
		FacesConfiguration.getFacesConfig()
		                  .add(resource.getPathRelativeToClasspathElement());
		resource.close();
	}
}
