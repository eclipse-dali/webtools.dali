/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.schemagen;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jpt.jaxb.core.schemagen.internal.JptJaxbCoreMessages;

/**
 *  Generate a JAXB Schema
 *  
 * Current command-line arguments:
 *     [-s schema.xsd] - specifies the target schema
 *     [-c className] - specifies the fully qualified class name
 *     
 *     [-p packageName] - specifies the source package // @deprecated
 */
public class Main
{
	private String[] sourceClassNames;
	private String targetSchemaName;
	@SuppressWarnings("unused")
	private boolean isDebugMode;

	static public String NO_FACTORY_CLASS = "doesnt contain ObjectFactory.class";   //$NON-NLS-1$

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
		String result = (jaxbContext != null) ? 
				this.bind(JptJaxbCoreMessages.SCHEMA_GENERATED, this.targetSchemaName) : 
				this.bind(JptJaxbCoreMessages.SCHEMA_NOT_CREATED, this.targetSchemaName);
		System.out.println(result);
    }
	
	private JAXBContext buildJaxbContext() {
		System.out.println(this.getString(JptJaxbCoreMessages.LOADING_CLASSES));
		JAXBContext jaxbContext = null;
			try {
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				
				Class[] sourceClasses = this.buildSourceClasses(this.sourceClassNames, loader);
				
				jaxbContext = JAXBContext.newInstance(sourceClasses);
			}
			catch(JAXBException ex) {
				this.handleException(ex);
			}
			return jaxbContext;
	 }
	
	private void generateSchema(JAXBContext jaxbContext) {
		System.out.println(this.getString(JptJaxbCoreMessages.GENERATING_SCHEMA));
		System.out.flush();
		
		SchemaOutputResolver schemaOutputResolver = 
			new JptSchemaOutputResolver(this.targetSchemaName);

		try {
			jaxbContext.generateSchema(schemaOutputResolver);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    private Class[] buildSourceClasses(String[] classNames, ClassLoader loader) {

		ArrayList<Class> sourceClasses = new ArrayList<Class>(classNames.length);
		for(String className: classNames) {
			try {
				sourceClasses.add(loader.loadClass(className));
					System.out.println(className);
			}
			catch (ClassNotFoundException e) {
				System.err.println(this.bind(JptJaxbCoreMessages.NOT_FOUND, className));
			}
		}
		System.out.flush();
		return sourceClasses.toArray(new Class[0]);
    }
	
	private void handleException(JAXBException ex) {
		String message = ex.getMessage();
		Throwable linkedEx = ex.getLinkedException();
		if(message != null && message.indexOf(NO_FACTORY_CLASS) > -1) {
			System.err.println(message);
		}
		else if(linkedEx != null && linkedEx instanceof ClassNotFoundException) {
			String errorMessage = this.bind(JptJaxbCoreMessages.CONTEXT_FACTORY_NOT_FOUND, linkedEx.getMessage());
			System.err.println(errorMessage);
		}
		else {
			ex.printStackTrace();
		}
	}
	
	private String getString(String key) {
		return JptJaxbCoreMessages.getString(key);
	}
	
	private String bind(String key, Object argument) {
		return MessageFormat.format(this.getString(key), argument);
	}

	// ********** argument queries **********
    
	private String[] getSourceClassNames(String[] args) {

		return this.getAllArgumentValue("-c", args);   //$NON-NLS-1$
	}
	
	private String getTargetSchemaName(String[] args) {

		return this.getArgumentValue("-s", args);   //$NON-NLS-1$
	}

	private boolean getDebugMode(String[] args) {

		return this.argumentExists("-debug", args);   //$NON-NLS-1$
	}
	
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
	
	private String[] getAllArgumentValue(String argName, String[] args) {
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

}

// ********** inner class **********

class JptSchemaOutputResolver extends SchemaOutputResolver {
	
	private final String targetSchemaName;
	
	protected JptSchemaOutputResolver(String targetSchemaName) {
		this.targetSchemaName = targetSchemaName;
	}
	
	 @Override
    public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {

        File file = new File(this.targetSchemaName );
        StreamResult result = new StreamResult(file);
        result.setSystemId(file.toURI().toURL().toString());
        return result;
    }
}
