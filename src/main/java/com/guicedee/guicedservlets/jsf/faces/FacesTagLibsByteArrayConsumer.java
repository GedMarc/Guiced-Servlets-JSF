package com.guicedee.guicedservlets.jsf.faces;

import com.guicedee.guicedservlets.jsf.FacesConfiguration;
import com.guicedee.logger.LogFactory;
import io.github.classgraph.Resource;
import io.github.classgraph.ResourceList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A consumer that reads FacesConfig.xml files into PersistenceUnit objects
 */
public class FacesTagLibsByteArrayConsumer
		implements ResourceList.ByteArrayConsumer
{
	/**
	 * The logger
	 */
	private static final Logger log = LogFactory.getLog("FacesTagLibsByteArrayConsumer");

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
		log.config("Loading tags file - " + resource.getURL());
		FacesConfiguration.getTagLibraries()
		                  .add(resource.getPathRelativeToClasspathElement());
		resource.close();
	}
}
