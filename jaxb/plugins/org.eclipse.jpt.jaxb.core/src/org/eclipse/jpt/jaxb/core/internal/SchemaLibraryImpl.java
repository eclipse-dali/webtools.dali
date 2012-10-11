/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.jaxb.core.JaxbPreferences;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.SchemaEntry;
import org.eclipse.jpt.jaxb.core.SchemaLibrary;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;
import org.eclipse.jpt.jaxb.core.xsd.XsdUtil;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class SchemaLibraryImpl
		implements SchemaLibrary {
	
	private JaxbProject project;
	
	private final List<SchemaEntryImpl> schemaEntries;
	
	private final Map<String, SchemaEntryImpl> impliedEntries;
	
	
	SchemaLibraryImpl(JaxbProject project) {
		this.project = project;
		this.schemaEntries = new Vector<SchemaEntryImpl>();
		this.impliedEntries = new Hashtable<String, SchemaEntryImpl>();
		readProjectPreferences();
	}
	
	
	public List<SchemaEntry> getSchemaEntries() {
		return Collections.<SchemaEntry>unmodifiableList(this.schemaEntries);
	}
	
	public List<String> getSchemaLocations() {
		return Collections.unmodifiableList(
				ListTools.list(
						new TransformationIterable<SchemaEntry, String>(this.schemaEntries) {
							@Override
							protected String transform(SchemaEntry o) {
								return o.getLocation();
							}
						}));
	}
	
	public void setSchemaLocations(List<String> schemaLocations) {
		for (SchemaEntryImpl entry : this.schemaEntries) {
			entry.dispose();
		}
		this.schemaEntries.clear();
		JaxbPreferences.setSchemaLocations(this.project.getProject(), schemaLocations);
		readProjectPreferences();
	}
	
	public XsdSchema getSchema(String namespace) {
		for (SchemaEntry entry : this.schemaEntries) {
			if (ObjectTools.equals(namespace, entry.getNamespace())) {
				return entry.getXsdSchema(namespace);
			}
		}
		
		if (! this.impliedEntries.containsKey(namespace)) {
			if (! ObjectTools.equals(XsdUtil.getResolvedUri(namespace), namespace)) {
				// the namespace itself resolves to a location.  add it as an implied entry
				this.impliedEntries.put(namespace, new SchemaEntryImpl(namespace));
			}
			else {
				return null;
			}
		}
		
		return this.impliedEntries.get(namespace).getXsdSchema(namespace);
	}
	
	protected void readProjectPreferences() {
		List<String> schemas = JaxbPreferences.getSchemaLocations(this.project.getProject());
		for (String schemaLocation : schemas) {
			SchemaEntryImpl entry = new SchemaEntryImpl(schemaLocation);
			this.schemaEntries.add(entry);
		}
	}
	
	public void refreshSchema(String namespace) {
		for (SchemaEntryImpl entry : this.schemaEntries) {
			if (ObjectTools.equals(namespace, entry.getNamespace())) {
				entry.refresh();
			}
		}
	}
	
	public void refreshAllSchemas() {
		for (SchemaEntryImpl entry : this.schemaEntries) {
			entry.refresh();
		}
	}
	
	void dispose() {
		for (SchemaEntryImpl entry : this.schemaEntries) {
			entry.dispose();
		}
		for (SchemaEntryImpl entry : this.impliedEntries.values()) {
			entry.dispose();
		}
		
		this.schemaEntries.clear();
		this.impliedEntries.clear();
	}
	
	public void validate(List<IMessage> messages) {
		Bag<String> namespaces = new HashBag<String>();
		
		for (SchemaEntry entry : this.schemaEntries) {
			namespaces.add(entry.getNamespace());
			
			if (! entry.isLoaded()) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.PROJECT__UNRESOLVED_SCHEMA,
								new String[] { entry.getLocation() },
								this.project));
			}
		}
		
		for (Iterator<String> stream = namespaces.uniqueIterator(); stream.hasNext(); ) {
			String namespace = stream.next();
			if (namespaces.count(namespace) > 1) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.PROJECT__DUPLICATE_NAMESPACE,
								new String[] { namespace },
								this.project));
			}
		}
	}
}
