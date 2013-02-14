/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbTypeMapping;
import org.eclipse.jpt.jaxb.core.context.TypeKind;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextRoot;
import org.eclipse.jpt.jaxb.core.resource.jaxbprops.JaxbPropertiesResource;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbProject;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlEnum;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm.OxmFileImpl;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.validation.JptJaxbEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJaxbContextRootImpl
		extends AbstractJaxbContextRoot
		implements ELJaxbContextRoot {
	
	// store as SortedSet ordered by file path
	private SortedSet<OxmFile> oxmFiles;
	
	// store map of type mappings for easiest (and fastest) lookup
	private Hashtable<String, OxmTypeMapping> oxmTypeMappingMap;
	
	
	public ELJaxbContextRootImpl(JaxbProject jaxbProject) {
		super(jaxbProject);
	}
	
	
	// ***** overrides *****
	
	@Override
	public ELJaxbProject getJaxbProject() {
		return (ELJaxbProject) super.getJaxbProject();
	}
	
	@Override
	public JaxbClassMapping getClassMapping(String typeName) {
		OxmTypeMapping mapping = this.oxmTypeMappingMap.get(typeName);
		if (mapping != null) {
			return (mapping.getTypeKind() == TypeKind.CLASS) ? 
					(JaxbClassMapping) mapping
					: null;  // mapping is not a class mapping, return null
		}
		return super.getClassMapping(typeName);
	}
	
	
	// ***** initialize/update *****
	
	@Override
	protected void initialize() {
		
		// initialize oxm files and type mappings *first*
		initOxmFiles();
		// map should at least not be null
		this.oxmTypeMappingMap = new Hashtable<String, OxmTypeMapping>();
		super.initialize();
	}
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		for (OxmFile oxmFile : getOxmFiles()) {
			oxmFile.synchronizeWithResourceModel();
		}
	}
	
	@Override
	public void update() {
		// rebuild type mapping map before anything
		rebuildOxmTypeMappingMap();
		
		// update oxm files before calling update on superclass
		updateOxmFiles();
		
		super.update();
	}
	
	protected void rebuildOxmTypeMappingMap() {
		synchronized (this.oxmTypeMappingMap) {
			Hashtable<String, OxmTypeMapping> oldMap = new Hashtable<String, OxmTypeMapping>(this.oxmTypeMappingMap);
			this.oxmTypeMappingMap.clear();
			for (OxmFile oxmFile : getOxmFiles()) {
				for (OxmXmlEnum oxmXmlEnum: oxmFile.getXmlBindings().getXmlEnums()) {
					String typeName = oxmXmlEnum.getTypeName().getFullyQualifiedName();
					if (! StringTools.isBlank(typeName)) {
						if (! this.oxmTypeMappingMap.containsKey(typeName)) {
							this.oxmTypeMappingMap.put(typeName, oxmXmlEnum);
						}
					}
				}
				for (OxmJavaType oxmJavaType : oxmFile.getXmlBindings().getJavaTypes()) {
					String typeName = oxmJavaType.getTypeName().getFullyQualifiedName();
					if (! StringTools.isBlank(typeName)) {
						if (! this.oxmTypeMappingMap.containsKey(typeName)) {
							this.oxmTypeMappingMap.put(typeName, oxmJavaType);
						}
					}
				}
			}
			if (! oldMap.equals(this.oxmTypeMappingMap)) {
				// no aspect associated with this.  this is just to cover the cases where this is
				// the only thing that changes, as no further update will be scheduled.
				fireStateChanged();
			}
		}
	}
	
	@Override
	protected Set<String> calculateInitialPackageNames() {
		Set<String> initialPackageNames = super.calculateInitialPackageNames();
		for (OxmFile oxmFile : getOxmFiles()) {
			String packageName = oxmFile.getPackageName();
			if (! StringTools.isBlank(packageName)) {
				initialPackageNames.add(packageName);
			}
		}
		return initialPackageNames;
	}
	
	
	// ***** oxm files *****
	
	public Iterable<OxmFile> getOxmFiles() {
		synchronized (this.oxmFiles) {
			return IterableTools.cloneSnapshot(this.oxmFiles);
		}
	}
	
	public int getOxmFilesSize() {
		return this.oxmFiles.size();
	}
	
	protected void addOxmFile(OxmFile oxmFile) {
		addItemToCollection(oxmFile, this.oxmFiles, OXM_FILES_COLLECTION);
	}
	
	protected void removeOxmFile(OxmFile oxmFile) {
		removeItemFromCollection(oxmFile, this.oxmFiles, OXM_FILES_COLLECTION);
	}
	
	public OxmFile getOxmFile(String packageName) {
		for (OxmFile oxmFile : getOxmFiles()) {
			if (packageName.equals(oxmFile.getPackageName())) {
				return oxmFile;
			}
		}
		return null;
	}
	
	protected OxmFile buildOxmFile(JptXmlResource oxmResource) {
		return new OxmFileImpl(this, oxmResource);
	}
	
	protected void initOxmFiles() {
		// can't do this statically, since this gets called during constructor
		this.oxmFiles = Collections.synchronizedSortedSet(
				new TreeSet<OxmFile>(
						new Comparator<OxmFile>() {
							public int compare(OxmFile of1, OxmFile of2) {
								return of1.getResource().getProjectRelativePath().toString().compareTo(
									of2.getResource().getProjectRelativePath().toString());
							}
						}));
		synchronized (this.oxmFiles) {
			for (JptXmlResource oxmResource : getJaxbProject().getOxmResources()) {
				this.oxmFiles.add(buildOxmFile(oxmResource));
			}
		}
	}
	
	protected void updateOxmFiles() {
		Collection<JptXmlResource> 
				unmatchedOxmResources = CollectionTools.collection(getJaxbProject().getOxmResources());
		
		for (OxmFile oxmFile : getOxmFiles()) {
			JptXmlResource oxmResource = oxmFile.getOxmResource();
			if (! unmatchedOxmResources.remove(oxmResource)) {
				removeOxmFile(oxmFile);
			}
			else {
				oxmFile.update();
			}
		}
		
		for (JptXmlResource oxmResource : unmatchedOxmResources) {
			addOxmFile(buildOxmFile(oxmResource));
		}
	}
	
	
	// ***** misc *****
	
	@Override
	public JaxbTypeMapping getTypeMapping(String typeName) {
		OxmTypeMapping oxmTypeMapping = getOxmTypeMapping(typeName);
		return (oxmTypeMapping != null) ? oxmTypeMapping : super.getTypeMapping(typeName);
	}
	
	public OxmTypeMapping getOxmTypeMapping(String typeName) {
		return this.oxmTypeMappingMap.get(typeName);
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		return TextRange.Empty.instance(); //?
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateJaxbProperties(messages, reporter);
		
		for (OxmFile oxmFile : getOxmFiles()) {
			oxmFile.validate(messages, reporter);
		}
	}
	
	protected void validateJaxbProperties(List<IMessage> messages, IReporter reporter) {
		String factoryProp = "javax.xml.bind.context.factory";
		String factoryPropValue = "org.eclipse.persistence.jaxb.JAXBContextFactory";
			
		for (JaxbPackage jp : getPackages()) {
			String pn = jp.getName();
			JaxbPropertiesResource jpr = getJaxbProject().getJaxbPropertiesResource(pn);
			if (jpr != null && ObjectTools.equals(jpr.getProperty(factoryProp), factoryPropValue)) {
				return;
			}
		}
		
		messages.add(
				ELJaxbValidationMessageBuilder.buildMessage(
						IMessage.HIGH_SEVERITY,
						JptJaxbEclipseLinkCoreValidationMessages.PROJECT_MISSING_ECLIPSELINK_JAXB_CONTEXT_FACTORY,
						getJaxbProject()));
	}
}
