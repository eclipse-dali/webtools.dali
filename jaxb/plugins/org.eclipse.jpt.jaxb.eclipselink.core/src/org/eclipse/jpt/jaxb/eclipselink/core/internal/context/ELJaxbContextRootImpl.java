/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.SnapshotCloneIterable;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextRoot;
import org.eclipse.jpt.jaxb.core.resource.jaxbprops.JaxbPropertiesResource;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbProject;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm.OxmFileImpl;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class ELJaxbContextRootImpl
		extends AbstractJaxbContextRoot
		implements ELJaxbContextRoot {
	
	// store as SortedSet ordered by file path
	private SortedSet<OxmFile> oxmFiles;
	
	
	public ELJaxbContextRootImpl(JaxbProject jaxbProject) {
		super(jaxbProject);
	}
	
	
	// ***** overrides *****
	
	@Override
	public ELJaxbProject getJaxbProject() {
		return (ELJaxbProject) super.getJaxbProject();
	}
	
	// ***** initialize/update *****
	
	@Override
	protected void initialize() {
		
		// can't do this statically, since this gets called during constructor
		this.oxmFiles = Collections.synchronizedSortedSet(
				new TreeSet<OxmFile>(
						new Comparator<OxmFile>() {
							public int compare(OxmFile of1, OxmFile of2) {
								return of1.getResource().getProjectRelativePath().toString().compareTo(
									of2.getResource().getProjectRelativePath().toString());
							}
						})); 
		
		// initialize oxm files *first*
		synchronized (this.oxmFiles) {
			for (JptXmlResource oxmResource : getJaxbProject().getOxmResources()) {
				this.oxmFiles.add(buildOxmFile(oxmResource));
			}
		}
		
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
		
		// update oxm files *first* 
		
		Collection<JptXmlResource> 
				unmatchedOxmResources = CollectionTools.collection(getJaxbProject().getOxmResources());
		
		for (OxmFile oxmFile : getOxmFiles()) {
			JptXmlResource oxmResource = oxmFile.getOxmResource();
			if (! unmatchedOxmResources.remove(oxmResource)) {
				removeOxmFile(oxmFile);
			}
		}
		
		for (JptXmlResource oxmResource : unmatchedOxmResources) {
			addOxmFile(buildOxmFile(oxmResource));
		}
		
		super.update();
	}
	
	
	// ***** oxm files *****
	
	public Iterable<OxmFile> getOxmFiles() {
		synchronized (this.oxmFiles) {
			return new SnapshotCloneIterable(this.oxmFiles);
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
						ELJaxbValidationMessages.PROJECT_MISSING_ECLIPSELINK_JAXB_CONTEXT_FACTORY,
						getJaxbProject()));
	}
}
