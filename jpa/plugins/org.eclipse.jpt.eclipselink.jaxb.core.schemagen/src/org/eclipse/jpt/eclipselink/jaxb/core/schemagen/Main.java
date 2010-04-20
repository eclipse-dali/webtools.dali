/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.jaxb.core.schemagen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.jaxb.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.stream.StreamResult;

import javax.xml.transform.Result;

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
		System.out.println("MOXy generating schema...");    //$NON-NLS-1$
		
        // Create the JAXBContext
        JAXBContext jaxbContext = this.buildJaxbContext();

        // Generate an XML Schema
        if(jaxbContext != null) {
        	SchemaOutputResolver schemaOutputResolver = 
        		new JptSchemaOutputResolver(this.targetSchemaName);
        	
        	jaxbContext.generateSchema(schemaOutputResolver);
        	
        	System.out.println("\nSchema " + this.targetSchemaName + " generated");   //$NON-NLS-1$
        }
    }
	
	private JAXBContext buildJaxbContext() {
        JAXBContext jaxbContext = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			
			Class[] sourceClasses = this.buildSourceClasses(this.sourceClassNames, loader);
			
			jaxbContext = (JAXBContext)JAXBContext.newInstance(sourceClasses);

		}
		catch (JAXBException e) {
			String message = e.getMessage();
			if(message.indexOf(NO_FACTORY_CLASS) > -1) {
				System.err.println(message);
			}
			else {
				e.printStackTrace();
			}
			System.err.println("\nSchema " + this.targetSchemaName + " not created");    //$NON-NLS-1$
		}
		return jaxbContext;
	}
    
    private Class[] buildSourceClasses(String[] classNames, ClassLoader loader) {

		ArrayList<Class> sourceClasses = new ArrayList<Class>(classNames.length);
		for(String className: classNames) {
			try {
				sourceClasses.add(loader.loadClass(className));
					System.out.println(className);   //$NON-NLS-1$
			}
			catch (ClassNotFoundException e) {
				System.err.println("\n\tNot found: " + className);   //$NON-NLS-1$
			}
		}
		return sourceClasses.toArray(new Class[0]);
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
