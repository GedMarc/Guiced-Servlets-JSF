package com.guicedee.guicedservlets.jsf.implementations;

import io.github.classgraph.ResourceList;
import com.guicedee.guicedinjection.interfaces.IFileContentsScanner;
import com.guicedee.logger.LogFactory;
import com.guicedee.jpms.schemas.facesconfig.FacesConfigType;

import java.net.URI;
import java.util.*;
import java.util.logging.Logger;

public class FacesConfigFileHandler
		implements IFileContentsScanner
{
	/**
	 * A list of all registered faces config units
	 */
	private static final Map<URI, FacesConfigType> facesConfigFiles = new LinkedHashMap<>();
	/**
	 * The logger
	 */
	private static final Logger log = LogFactory.getLog("FacesConfigFileScanner");


	/**
	 * Returns a contents processer to run on match
	 *
	 * @return the maps of file identifiers and contents
	 */
	@Override
	public Map<String, ResourceList.ByteArrayConsumer> onMatch()
	{
		Map<String, ResourceList.ByteArrayConsumer> map = new HashMap<>();
		/*log.info("Loading Faces Config Consumer");
		ResourceList.ByteArrayConsumer processor = new FacesConfigByteArrayConsumer();
		map.put("faces-config.xml", processor);*/
		return map;
	}

	public static Map<URI, FacesConfigType> getFacesConfigFiles() {
		return facesConfigFiles;
	}
}