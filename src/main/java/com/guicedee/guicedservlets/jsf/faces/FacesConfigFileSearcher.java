package com.guicedee.guicedservlets.jsf.faces;

import com.guicedee.guicedinjection.interfaces.IFileContentsScanner;
import com.guicedee.logger.LogFactory;
import io.github.classgraph.ResourceList;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Loads persistence units from persistence files as found on the registered classpath
 */
@SuppressWarnings("unused")
public class FacesConfigFileSearcher
		implements IFileContentsScanner
{
	/**
	 * The logger
	 */
	private static final Logger log = LogFactory.getLog("FacesConfigFileSearcher");

	/**
	 * A new persistence file handler
	 */
	public FacesConfigFileSearcher()
	{
		//No Config Required
	}

	/**
	 * Returns a contents processer to run on match
	 *
	 * @return the maps of file identifiers and contents
	 */
	@Override
	public Map<String, ResourceList.ByteArrayConsumer> onMatch()
	{
		Map<String, ResourceList.ByteArrayConsumer> map = new HashMap<>();
		log.info("Loading Faces Config File Handling - faces-config.xml");
		ResourceList.ByteArrayConsumer processor = new FacesConfigByteArrayConsumer();
		map.put("faces-config.xml", processor);
		return map;
	}

}
