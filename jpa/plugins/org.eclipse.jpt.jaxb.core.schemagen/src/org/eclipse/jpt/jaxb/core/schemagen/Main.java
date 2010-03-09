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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.stream.StreamResult;

import javax.xml.transform.Result;

/**
 *  Generate a JAXB Schema
 *  
 * Current command-line arguments:
 *     [-s schema.xsd] - specifies the target schema
 *     [-p packageName] - specifies the source package 
 * 
 *  Required JRE 1.6 and eclipselink.jar to run
 */
public class Main
{
	private String sourcePackageName;
	private String targetSchemaName;
	private boolean isDebugMode;

	// ********** static methods **********
	
	public static void main(String[] args) throws Exception {
		new Main().execute(args);
	}
	
	// ********** constructors **********

	private Main() {
		super();
	}

	// ********** behavior **********
	
	protected void execute(String[] args) throws Exception {
		
		this.initializeWith(args);
		
		this.generate();
	}
		
    private void generate() throws Exception {
        // Create the JAXBContext
        JAXBContext jaxbContext = JAXBContext.newInstance(this.sourcePackageName);

        // Generate an XML Schema
		SchemaOutputResolver schemaOutputResolver = 
			new JptSchemaOutputResolver(this.targetSchemaName);
		
		jaxbContext.generateSchema(schemaOutputResolver);

		System.out.println("\nSchema " + this.targetSchemaName + " generated");
    }
    
	private void initializeWith(String[] args) {
    	this.sourcePackageName = this.getSourcePackageName(args);
    	this.targetSchemaName = this.getTargetSchemaName(args);

		this.isDebugMode = this.getDebugMode(args);
	}

	// ********** argument queries **********
    
	private String getSourcePackageName(String[] args) {

		return this.getArgumentValue("-p", args);
	}
	
	private String getTargetSchemaName(String[] args) {

		return this.getArgumentValue("-s", args);
	}

	private boolean getDebugMode(String[] args) {

		return this.argumentExists("-debug", args);
	}
	
	private String getArgumentValue(String argument, String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.toLowerCase().equals(argument)) {
				int j = i + 1;
				if (j < args.length) {
					return args[j];
				}
			}
		}
		return null;
	}
	
	private boolean argumentExists(String argument, String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.toLowerCase().equals(argument)) {
				return true;
			}
		}
		return false;
	}

}

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
