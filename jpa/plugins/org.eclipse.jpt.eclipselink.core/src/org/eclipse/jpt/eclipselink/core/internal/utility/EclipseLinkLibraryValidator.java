/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.utility;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.util.IClassFileReader;
import org.eclipse.jdt.core.util.IFieldInfo;
import org.eclipse.jpt.core.internal.utility.KeyClassesValidator;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCoreMessages;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jst.common.project.facet.core.libprov.user.UserLibraryProviderInstallOperationConfig;
import org.eclipse.jst.common.project.facet.core.libprov.user.UserLibraryValidator;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Version;

/**
 * Library validator for EclipseLink user libraries.
 * 
 * This has two facets:
 * 	- eclipselink version:  In order to validate that the correct eclipselink.jar is present in the 
 * 		user library, the extension using this should specify none or more version ranges using a 
 * 		standard library provider param expression, but with the prefix "version-range".  The actual
 * 		version of the eclipselink build will be checked against the union of these version ranges.
 * 		(e.g. <param name="validator.param.0" value="version-range:[2.0, 3.0)"/> )
 * - jpa key classes:  In order to validate that the correct jpa.jar is present in the user 
 * 		library (since version of that jar is impossible to check,) the extension using this should 
 * 		specify none or more key classes using a standard library provider param expression, but
 * 		with the prefix "key-class".  The user library will check that all specified key classes
 * 		are present.
 * 		(e.g. <param name="validator.param.1" value="key-class:javax.persistence.Entity"/> )
 */
public class EclipseLinkLibraryValidator
	extends UserLibraryValidator
{
	private final static String VERSION_CLASS_PATH = "org/eclipse/persistence/Version.class";
	
	private final static String VERSION_FIELD_NAME = "version";
	
	private final static String VERSION_RANGE_PREFIX = "version-range:";
	
	private final static String KEY_CLASS_PREFIX = "key-class:";
	
	private final List<VersionRange> versionRanges = new ArrayList<VersionRange>();
	
	private final List<String> keyClasses = new ArrayList<String>();
	
	
	private final KeyClassesValidator keyClassesValidator = new KeyClassesValidator();
	
	
	public EclipseLinkLibraryValidator() {
		super();
	}
	
	
	@Override
    public void init(final List<String> params) {
		for (String param : params) {
			if (param.startsWith(VERSION_RANGE_PREFIX)) {
				versionRanges.add(new VersionRange(param.replaceFirst(VERSION_RANGE_PREFIX, "")));
			}
			else if (param.startsWith(KEY_CLASS_PREFIX)) {
				keyClasses.add(param.replaceFirst(KEY_CLASS_PREFIX, ""));
			}
		}
		this.keyClassesValidator.init(this.keyClasses);
	}
	
	@Override
	public IStatus validate(UserLibraryProviderInstallOperationConfig config) {
		if (! this.keyClassesValidator.validate(config).isOK()) {
			return new Status(
					IStatus.ERROR, JptEclipseLinkCorePlugin.PLUGIN_ID,
					JptEclipseLinkCoreMessages.EclipseLinkLibraryValidator_invalidJpaLibrary);
		}
		
		Version version = null;
		for (IClasspathEntry cpe : config.resolve()) {
			String versionString = null;
			if (cpe.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
				IClassFileReader classReader = 
						ToolFactory.createDefaultClassFileReader(
							cpe.getPath().toFile().getAbsolutePath(), VERSION_CLASS_PATH, IClassFileReader.FIELD_INFOS);
				if (classReader != null) {
					for (IFieldInfo field : classReader.getFieldInfos()) {
						if (StringTools.stringsAreEqual(field.getName(), VERSION_FIELD_NAME.toCharArray())) {
							try {
								versionString = field.getConstantValueAttribute().getConstantValue().getStringValue();
							}
							catch (Exception e) {
								// potentially a bit could go wrong with that last line, but if any
								// assumptions aren't met, there's no value
							}
							break;
						}
					}
				}
				if (versionString != null) {
					if (version != null) {
						return new Status(
								IStatus.ERROR, JptEclipseLinkCorePlugin.PLUGIN_ID,
								JptEclipseLinkCoreMessages.EclipseLinkLibraryValidator_multipleEclipseLinkVersions);
					}
					else {
						version = new Version(versionString);
					}
				}
			}
		}
		
		if (version == null) {
			return new Status(
					IStatus.ERROR, JptEclipseLinkCorePlugin.PLUGIN_ID, 
					JptEclipseLinkCoreMessages.EclipseLinkLibraryValidator_noEclipseLinkVersion);
		}
		
		for (VersionRange versionRange : this.versionRanges) {
			if (! versionRange.isIncluded(version)) {
				return new Status(
						IStatus.ERROR, JptEclipseLinkCorePlugin.PLUGIN_ID, 
						JptEclipseLinkCoreMessages.EclipseLinkLibraryValidator_improperEclipseLinkVersion);
			}
		}
		
		return Status.OK_STATUS;
	}
}
