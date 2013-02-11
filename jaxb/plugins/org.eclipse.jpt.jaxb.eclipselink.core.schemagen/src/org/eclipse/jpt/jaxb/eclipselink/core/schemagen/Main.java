/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.schemagen;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jpt.jaxb.eclipselink.core.schemagen.internal.Tools;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

/**
 *  Generate a EclipseLink JAXB Schema
 *  
 * Current command-line arguments:
 *     [-s schema.xsd] - specifies the target schema
 *     [-c className] - specifies the fully qualified class name
 */
public class Main
{
	private String[] sourceClassNames;
	private String targetSchemaName;
	@SuppressWarnings("unused")
	private boolean isDebugMode;

	static public String NO_FACTORY_CLASS = "doesnt contain ObjectFactory.class";   //$NON-NLS-1$
	static public String CANNOT_BE_CAST_TO_JAXBCONTEXT = "cannot be cast to org.eclipse.persistence.jaxb.JAXBContext";   //$NON-NLS-1$

	// ********** static methods **********
	
	public static void main(String[] args) {
		new Main().execute(args);
	}
	
	// ********** constructors **********

	private Main() {
		super();
	}

	// ********** behavior **********
	
	protected void execute(String[] args) {
		
		this.initializeWith(args);
		
		this.generate();
	}

	// ********** internal methods **********
    
	private void initializeWith(String[] args) {
    	this.sourceClassNames = this.getSourceClassNames(args);
    	this.targetSchemaName = this.getTargetSchemaName(args);

		this.isDebugMode = this.getDebugMode(args);
	}

	private void generate() {
        // Create the JAXBContext
        JAXBContext jaxbContext = this.buildJaxbContext();

        // Generate an XML Schema
        if(jaxbContext != null) {
			this.generateSchema(jaxbContext);
		}
        else {
        	Tools.logMessage(INFO, Tools.bind(JptJaxbEclipseLinkCoreSchemagenMessages.SCHEMA_NOT_CREATED, this.targetSchemaName));
        	this.generationFailed();
        }
		System.out.flush();
	}
	
	private JAXBContext buildJaxbContext() {
		Tools.logMessage(INFO, Tools.getString(JptJaxbEclipseLinkCoreSchemagenMessages.LOADING_CLASSES));
        JAXBContext jaxbContext = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			
			Class[] sourceClasses = this.buildSourceClasses(this.sourceClassNames, loader);
			
			//call MOXy JAXBContextFactory directly.  This eliminates the need to have the JAXB properties file in place
			//in time for the generation.
			jaxbContext = (JAXBContext)JAXBContextFactory.createContext(sourceClasses, Collections.<String,Object>emptyMap());
		}
		catch (JAXBException ex) {
			this.handleJaxbException(ex);
		}
		catch (ClassCastException ex) {
			this.handleClassCastException(ex);
		}
		return jaxbContext;
	}
	
	private void generateSchema(JAXBContext jaxbContext) {
		Tools.logMessage(INFO, Tools.getString(JptJaxbEclipseLinkCoreSchemagenMessages.GENERATING_SCHEMA));
		System.out.flush();

    	SchemaOutputResolver schemaOutputResolver = 
    		new JptSchemaOutputResolver(this.targetSchemaName);

		try {
			jaxbContext.generateSchema(schemaOutputResolver);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
    
    private Class[] buildSourceClasses(String[] classNames, ClassLoader loader) {

		ArrayList<Class> sourceClasses = new ArrayList<Class>(classNames.length);
		for(String className: classNames) {
			try {
				sourceClasses.add(loader.loadClass(className));
				Tools.logMessage(INFO, className);
			}
			catch (ClassNotFoundException e) {
				Tools.logMessage(SEVERE, Tools.bind(JptJaxbEclipseLinkCoreSchemagenMessages.NOT_FOUND, className));
			}
		}
		System.out.flush();
		return sourceClasses.toArray(new Class[0]);
    }
	
	private void handleJaxbException(JAXBException ex) {
		String message = ex.getMessage();
		Throwable linkedEx = ex.getLinkedException();
		if(message != null && message.indexOf(NO_FACTORY_CLASS) > -1) {
			Tools.logMessage(SEVERE, message);
		}
		else if(linkedEx != null && linkedEx instanceof ClassNotFoundException) {
			String errorMessage = Tools.bind(
				JptJaxbEclipseLinkCoreSchemagenMessages.CONTEXT_FACTORY_NOT_FOUND, linkedEx.getMessage());
			Tools.logMessage(SEVERE, errorMessage);
		}
		else {
			ex.printStackTrace();
		}
	}
	
	private void handleClassCastException(ClassCastException ex) {
		String message = ex.getMessage();
		if(message != null && message.indexOf(CANNOT_BE_CAST_TO_JAXBCONTEXT) > -1) {
			Tools.logMessage(SEVERE, Tools.getString(JptJaxbEclipseLinkCoreSchemagenMessages.PROPERTIES_FILE_NOT_FOUND));
		}
		else {
			ex.printStackTrace();
		}
	}

	// ********** argument queries **********
    
	private String[] getSourceClassNames(String[] args) {

		return this.getAllArgumentValues("-c", args);   //$NON-NLS-1$
	}
	
	private String getTargetSchemaName(String[] args) {

		return this.getArgumentValue("-s", args);   //$NON-NLS-1$
	}

	private boolean getDebugMode(String[] args) {

		return this.argumentExists("-debug", args);   //$NON-NLS-1$
	}

	// ********** private methods **********

	private String getArgumentValue(String argName, String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.toLowerCase().equals(argName)) {
				int j = i + 1;
				if (j < args.length) {
					return args[j];
				}
			}
		}
		return null;
	}
	
	private String[] getAllArgumentValues(String argName, String[] args) {
		List<String> argValues = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.toLowerCase().equals(argName)) {
				int j = i + 1;
				if (j < args.length) {
					argValues.add(args[j]);
					i++;
				}
			}
		}
		return argValues.toArray(new String[0]);
	}
	
	private boolean argumentExists(String argName, String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.toLowerCase().equals(argName)) {
				return true;
			}
		}
		return false;
	}
	
	private void generationFailed() {
		System.exit(1);
	}

}

// ********** inner class **********

class JptSchemaOutputResolver extends SchemaOutputResolver {
	
	private String defaultSchemaName;

	// ********** constructor **********
	
	protected JptSchemaOutputResolver(String defaultSchemaName) {
		this.defaultSchemaName = defaultSchemaName;
	}

	// ********** overrides **********

	 @Override
    public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {

        String filePath = (Tools.stringIsEmpty(namespaceURI)) ? 
        		this.buildFileNameFrom(this.defaultSchemaName, suggestedFileName) : 
        		this.modifyFileName(namespaceURI);

		File file = new File(filePath);
        StreamResult result = new StreamResult(file);
        result.setSystemId(file.toURI().toURL().toString());

        Tools.logMessage(INFO, Tools.bind(JptJaxbEclipseLinkCoreSchemagenMessages.SCHEMA_GENERATED, file));
        return result;
    }

		// ********** private methods **********

	 private String buildFileNameFrom(String fileName, String suggestedFileName) {

		 fileName = Tools.stripExtension(fileName);

		 if(Tools.stringIsEmpty(fileName)) {
			 return suggestedFileName;
		 }
		 String number = Tools.extractFileNumber(suggestedFileName);

		 fileName = this.buildFileName(fileName, number);
		 return Tools.appendXsdExtension(fileName);
	 }

	 private String buildFileName(String fileName, String number) {

		 if(Tools.stringIsEmpty(number)) {
			 return fileName;
		 }
		 return (number.equals("0")) ? fileName : fileName + number;   //$NON-NLS-1$
	 }

	 private String modifyFileName(String namespaceURI) throws IOException {

		 String dir = Tools.extractDirectory(this.defaultSchemaName);

		 String fileName = Tools.stripProtocol(namespaceURI);
		 fileName = fileName.replaceAll("/", "_");		//$NON-NLS-1$
		 fileName = Tools.appendXsdExtension(fileName);

		String result = (Tools.stringIsEmpty(dir)) ? fileName : dir + File.separator + fileName;
		
		return result;
	 }

}
