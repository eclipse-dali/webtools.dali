/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.general;

import java.util.ListIterator;
import java.util.Map;

import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 *  EclipseLinkGeneralProperties
 */
public class EclipseLinkGeneralProperties extends EclipseLinkPersistenceUnitProperties
	implements GeneralProperties
{
	// ********** EclipseLink properties **********
	private Boolean excludeEclipselinkOrm;

	// ********** constructors **********
	public EclipseLinkGeneralProperties(PersistenceUnit parent, ListValueModel<Property> propertyListAdapter) {
		super(parent, propertyListAdapter);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		
		this.excludeEclipselinkOrm = 
			this.getBooleanValue(ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM);
	}

	/**
	 * Initialize and add listeners to the persistence unit.
	 */
	@Override
	protected void initialize(PersistenceUnit parent, ListValueModel<Property> propertyListAdapter) {
		super.initialize(parent, propertyListAdapter);

		this.getPersistenceUnit().addPropertyChangeListener(
			PersistenceUnit.NAME_PROPERTY,
			this.buildNameChangeListener());

		this.getPersistenceUnit().addPropertyChangeListener(
			PersistenceUnit.PROVIDER_PROPERTY,
			this.buildProviderChangeListener());

		this.getPersistenceUnit().addPropertyChangeListener(
			PersistenceUnit.DESCRIPTION_PROPERTY,
			this.buildDescriptionChangeListener());

		this.getPersistenceUnit().addListChangeListener(
			PersistenceUnit.SPECIFIED_CLASS_REFS_LIST,
			this.buildClassRefsChangeListener());

		this.getPersistenceUnit().addListChangeListener(
			PersistenceUnit.SPECIFIED_MAPPING_FILE_REFS_LIST,
			this.buildMappingFileRefsChangeListener());
		
		this.getPersistenceUnit().addPropertyChangeListener(
			PersistenceUnit.SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY,
			this.buildExcludeUnlistedClassesChangeListener());
	}

	// ********** behavior **********
	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = EclipseLink property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM,
			EXCLUDE_ECLIPSELINK_ORM_PROPERTY);
	}

	public void propertyChanged(PropertyChangeEvent event) {
		String aspectName = event.getAspectName();
		if (aspectName.equals(EXCLUDE_ECLIPSELINK_ORM_PROPERTY)) {
			this.excludeEclipselinkOrmChanged(event);
		}
		else {
			throw new IllegalArgumentException("Illegal event received - property not applicable: " + aspectName);
		}
		return;
	}

	// ********** PersistenceUnit properties **********
	
	// ********** Name **********
	public String getName() {
		return this.getPersistenceUnit().getName();
	}
	
	public void setName(String newName) {
		this.getPersistenceUnit().setName( newName);
	}

	private void nameChanged(PropertyChangeEvent event) {
		this.firePropertyChanged(NAME_PROPERTY, event.getOldValue(), event.getNewValue());
	}
	
	public String getDefaultName() {
		return DEFAULT_NAME;
	}

	protected PropertyChangeListener buildNameChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				EclipseLinkGeneralProperties.this.nameChanged(event);
			}
		};
	}

	// ********** Provider **********
	
	public String getProvider() {
		return this.getPersistenceUnit().getProvider();
	}
	
	public void setProvider(String newProvider) {
		this.getPersistenceUnit().setProvider( newProvider);
	}

	private void providerChanged(PropertyChangeEvent event) {
		this.firePropertyChanged(PROVIDER_PROPERTY, event.getOldValue(), event.getNewValue());
	}
	
	public String getDefaultProvider() {
		return DEFAULT_PROVIDER;
	}

	protected PropertyChangeListener buildProviderChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				EclipseLinkGeneralProperties.this.providerChanged(event);
			}
		};
	}

	// ********** Description **********
	
	public String getDescription() {
		return this.getPersistenceUnit().getDescription();
	}
	
	public void setDescription(String newDescription) {
		this.getPersistenceUnit().setDescription( newDescription);
	}

	private void descriptionChanged(PropertyChangeEvent event) {
		this.firePropertyChanged(DESCRIPTION_PROPERTY, event.getOldValue(), event.getNewValue());
	}
	
	public String getDefaultDescription() {
		return DEFAULT_DESCRIPTION;
	}

	protected PropertyChangeListener buildDescriptionChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				EclipseLinkGeneralProperties.this.descriptionChanged(event);
			}
		};
	}

	// ********** ExcludeUnlistedClasses **********
	
	public Boolean getSpecifiedExcludeUnlistedClasses() {
		return this.getPersistenceUnit().getSpecifiedExcludeUnlistedClasses();
	}
	
	public void setSpecifiedExcludeUnlistedClasses(Boolean newExcludeUnlistedClasses) {
		this.getPersistenceUnit().setSpecifiedExcludeUnlistedClasses( newExcludeUnlistedClasses);
	}

	private void excludeUnlistedClassesChanged(PropertyChangeEvent event) {
		this.firePropertyChanged(SPECIFIED_EXCLUDE_UNLISTED_CLASSES_PROPERTY, event.getOldValue(), event.getNewValue());
	}
	
	public Boolean getDefaultExcludeUnlistedClasses() {
		return this.getPersistenceUnit().getDefaultExcludeUnlistedClasses();
	}

	protected PropertyChangeListener buildExcludeUnlistedClassesChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				EclipseLinkGeneralProperties.this.excludeUnlistedClassesChanged(event);
			}
		};
	}

	// ****** MappedClasses list *******
	
	public ListIterator<ClassRef> specifiedClassRefs() {
		return this.getPersistenceUnit().specifiedClassRefs();
	}

	public int specifiedClassRefsSize() {
		return this.getPersistenceUnit().specifiedClassRefsSize();
	}

	public ClassRef addSpecifiedClassRef() {
		return this.getPersistenceUnit().addSpecifiedClassRef();
	}
	
	public void removeSpecifiedClassRef(ClassRef classRef) {
		this.getPersistenceUnit().removeSpecifiedClassRef(classRef);
	}

	private void specifiedClassRefsChanged(ListChangeEvent event) {
		this.fireListChanged(SPECIFIED_CLASS_REFS_LIST);
	}
	
	private ListChangeListener buildClassRefsChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedClassRefsChanged(e);
			}

			public void itemsRemoved(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedClassRefsChanged(e);
			}

			public void itemsReplaced(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedClassRefsChanged(e);
			}

			public void itemsMoved(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedClassRefsChanged(e);
			}

			public void listCleared(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedClassRefsChanged(e);
			}

			public void listChanged(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedClassRefsChanged(e);
			}
		};
	}

	// ****** MappingFileRefs list *******
	
	public ListIterator<MappingFileRef> specifiedMappingFileRefs() {
		return this.getPersistenceUnit().specifiedMappingFileRefs();
	}

	public int specifiedMappingFileRefsSize() {
		return this.getPersistenceUnit().specifiedMappingFileRefsSize();
	}
	
	public MappingFileRef addSpecifiedMappingFileRef() {
		return this.getPersistenceUnit().addSpecifiedMappingFileRef();
	}

	public MappingFileRef addSpecifiedMappingFileRef(int index) {
		return this.getPersistenceUnit().addSpecifiedMappingFileRef(index);
	}
	
	public void removeSpecifiedMappingFileRef(MappingFileRef mappingFileRef) {
		this.getPersistenceUnit().removeSpecifiedMappingFileRef(mappingFileRef);
	}

	private void specifiedMappingFileRefsChanged(ListChangeEvent event) {
		this.fireListChanged(SPECIFIED_MAPPING_FILE_REFS_LIST);
	}
	
	private ListChangeListener buildMappingFileRefsChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedMappingFileRefsChanged(e);
			}

			public void itemsRemoved(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedMappingFileRefsChanged(e);
			}

			public void itemsReplaced(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedMappingFileRefsChanged(e);
			}

			public void itemsMoved(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedMappingFileRefsChanged(e);
			}

			public void listCleared(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedMappingFileRefsChanged(e);
			}

			public void listChanged(ListChangeEvent e) {
				EclipseLinkGeneralProperties.this.specifiedMappingFileRefsChanged(e);
			}
		};
	}

	// ********** EclipseLink properties **********
	
	// ********** ExcludeEclipselinkOrm **********
	public Boolean getExcludeEclipselinkOrm() {
		return this.excludeEclipselinkOrm;
	}

	public void setExcludeEclipselinkOrm(Boolean newExcludeEclipselinkOrm) {
		Boolean old = this.excludeEclipselinkOrm;
		this.excludeEclipselinkOrm = newExcludeEclipselinkOrm;
		this.putProperty(EXCLUDE_ECLIPSELINK_ORM_PROPERTY, newExcludeEclipselinkOrm);
		this.firePropertyChanged(EXCLUDE_ECLIPSELINK_ORM_PROPERTY, old, newExcludeEclipselinkOrm);
	}

	private void excludeEclipselinkOrmChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.excludeEclipselinkOrm;
		this.excludeEclipselinkOrm = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultExcludeEclipselinkOrm() {
		return DEFAULT_EXCLUDE_ECLIPSELINK_ORM;
	}
	
}
