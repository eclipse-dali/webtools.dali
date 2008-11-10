/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.PersistenceUnitProperties;

/**
 *  Customization
 */
public interface Customization extends PersistenceUnitProperties
{
	Boolean getDefaultThrowExceptions();
	Boolean getThrowExceptions();
	void setThrowExceptions(Boolean newThrowExceptions);
		static final String THROW_EXCEPTIONS_PROPERTY = "throwExceptionsProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_THROW_EXCEPTIONS = "eclipselink.orm.throw.exceptions";
		static final Boolean DEFAULT_THROW_EXCEPTIONS = Boolean.TRUE;
	
	Weaving getDefaultWeaving();
	Weaving getWeaving();
	void setWeaving(Weaving newWeaving);
		static final String WEAVING_PROPERTY = "weavingProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING = "eclipselink.weaving";
		static final Weaving DEFAULT_WEAVING = Weaving.true_;
 
	Boolean getDefaultWeavingLazy();
	Boolean getWeavingLazy();
	void setWeavingLazy(Boolean newWeavingLazy);
		static final String WEAVING_LAZY_PROPERTY = "weavingLazyProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING_LAZY = "eclipselink.weaving.lazy";
		static final Boolean DEFAULT_WEAVING_LAZY = Boolean.TRUE;
	
	Boolean getDefaultWeavingChangeTracking();
	Boolean getWeavingChangeTracking();
	void setWeavingChangeTracking(Boolean newWeavingChangeTracking);
		static final String WEAVING_CHANGE_TRACKING_PROPERTY = "weavingChangeTrackingProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING_CHANGE_TRACKING = "eclipselink.weaving.changetracking";
		static final Boolean DEFAULT_WEAVING_CHANGE_TRACKING = Boolean.TRUE;

	Boolean getDefaultWeavingFetchGroups();
	Boolean getWeavingFetchGroups();
	void setWeavingFetchGroups(Boolean newWeavingFetchGroups);
		static final String WEAVING_FETCH_GROUPS_PROPERTY = "weavingFetchGroupsProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING_FETCH_GROUPS = "eclipselink.weaving.fetchgroups";
		static final Boolean DEFAULT_WEAVING_FETCH_GROUPS = Boolean.TRUE;

	Boolean getDefaultWeavingInternal();
	Boolean getWeavingInternal();
	void setWeavingInternal(Boolean newWeavingInternal);
		static final String WEAVING_INTERNAL_PROPERTY = "weavingInternalProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING_INTERNAL = "eclipselink.weaving.internal";
		static final Boolean DEFAULT_WEAVING_INTERNAL = Boolean.TRUE;

	Boolean getDefaultWeavingEager();
	Boolean getWeavingEager();
	void setWeavingEager(Boolean newWeavingEager);
		static final String WEAVING_EAGER_PROPERTY = "weavingEagerProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_WEAVING_EAGER = "eclipselink.weaving.eager";
		static final Boolean DEFAULT_WEAVING_EAGER = Boolean.FALSE;

	String getDefaultDescriptorCustomizer();
	String getDescriptorCustomizer(String entityName);
	void setDescriptorCustomizer(String newDescriptorCustomizer, String entityName);
		static final String DESCRIPTOR_CUSTOMIZER_PROPERTY = "descriptorCustomizerProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_DESCRIPTOR_CUSTOMIZER = "eclipselink.descriptor.customizer.";
		static final String DEFAULT_DESCRIPTOR_CUSTOMIZER = null;	// no default

	ListIterator<ClassRef> sessionCustomizers();
	int sessionCustomizersSize();
	boolean sessionCustomizerExists(String sessionCustomizerClassName);
	ClassRef addSessionCustomizer(String newSessionCustomizerClassName);
	void removeSessionCustomizer(ClassRef classRef);
		static final String SESSION_CUSTOMIZER_LIST_PROPERTY = "sessionCustomizerListProperty";
		static final String SESSION_CUSTOMIZER_PROPERTY = "sessionCustomizerProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_SESSION_CUSTOMIZER = "eclipselink.session.customizer";

	ListIterator<String> entities();
	int entitiesSize();
	boolean entityExists(String entity);
	String addEntity(String entity);
	void removeEntity(String entity);
		static final String ENTITIES_LIST_PROPERTY = "entitiesListProperty";
		
	String getDefaultProfiler();
	String getProfiler();
	void setProfiler(String newProfiler);
	void setProfiler(Profiler newProfiler);
		static final String PROFILER_PROPERTY = "profilerProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_PROFILER = "eclipselink.profiler";
		static final String DEFAULT_PROFILER = 
			EclipseLinkPersistenceUnitProperties.getEclipseLinkStringValueOf(Profiler.no_profiler);

}
