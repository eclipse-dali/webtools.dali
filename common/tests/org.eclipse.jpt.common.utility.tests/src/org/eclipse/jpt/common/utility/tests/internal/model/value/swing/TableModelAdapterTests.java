/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.iterator.TransformationIterator;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.swing.TableModelAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class TableModelAdapterTests
	extends TestCase
{
	private Crowd crowd;
	TableModelEvent event;

	public TableModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.crowd = this.buildCrowd();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetRowCount() throws Exception {
		TableModelAdapter<Person> tableModelAdapter =  this.buildTableModelAdapter();
		assertEquals(0, tableModelAdapter.getRowCount());
		// we need to add a listener to wake up the adapter
		tableModelAdapter.addTableModelListener(this.buildTableModelListener());
		assertEquals(this.crowd.peopleSize(), tableModelAdapter.getRowCount());
	}

	public void testGetColumnCount() throws Exception {
		TableModelAdapter<Person> tableModelAdapter =  this.buildTableModelAdapter();
		assertEquals(PersonColumnAdapter.COLUMN_COUNT, tableModelAdapter.getColumnCount());
	}

	public void testGetValueAt() throws Exception {
		TableModelAdapter<Person> tableModelAdapter =  this.buildTableModelAdapter();
		tableModelAdapter.addTableModelListener(this.buildTableModelListener());

		List<String> sortedNames = this.sortedNames();
		for (int i = 0; i < this.crowd.peopleSize(); i++) {
			assertEquals(sortedNames.get(i), tableModelAdapter.getValueAt(i, PersonColumnAdapter.NAME_COLUMN));
		}
	}

	public void testSetValueAt() throws Exception {
		TableModelAdapter<Person> tableModelAdapter =  this.buildTableModelAdapter();
		this.event = null;
		tableModelAdapter.addTableModelListener(new TestTableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				TableModelAdapterTests.this.event = e;
			}
		});

		Person person = this.crowd.personNamed("Gollum");
		assertEquals(Person.EYE_COLOR_BLUE, person.getEyeColor());
		assertFalse(person.isEvil());
		assertEquals(0, person.getRank());

		for (int i = 0; i < tableModelAdapter.getRowCount(); i++) {
			if (tableModelAdapter.getValueAt(i, PersonColumnAdapter.NAME_COLUMN).equals("Gollum")) {
				tableModelAdapter.setValueAt(Person.EYE_COLOR_HAZEL, i, PersonColumnAdapter.EYE_COLOR_COLUMN);
				tableModelAdapter.setValueAt(Boolean.TRUE, i, PersonColumnAdapter.EVIL_COLUMN);
				tableModelAdapter.setValueAt(new Integer(-1), i, PersonColumnAdapter.RANK_COLUMN);
				break;
			}
		}
		assertNotNull(this.event);
		assertEquals(Person.EYE_COLOR_HAZEL, person.getEyeColor());
		assertTrue(person.isEvil());
		assertEquals(-1, person.getRank());
	}

	public void testAddRow() throws Exception {
		TableModelAdapter<Person> tableModelAdapter =  this.buildTableModelAdapter();
		this.event = null;
		tableModelAdapter.addTableModelListener(this.buildSingleEventListener());
		// add a person to the end of the list so we only trigger one event
		this.crowd.addPerson("Zzzzz");
		assertNotNull(this.event);
		assertEquals(TableModelEvent.INSERT, this.event.getType());
		assertEquals(TableModelEvent.ALL_COLUMNS, this.event.getColumn());
	}

	public void testRemoveRow() throws Exception {
		TableModelAdapter<Person> tableModelAdapter =  this.buildTableModelAdapter();
		this.event = null;
		tableModelAdapter.addTableModelListener(this.buildSingleEventListener());
		// removing a person should only trigger one event, since a re-sort is not needed
		this.crowd.removePerson(this.crowd.personNamed("Gollum"));
		assertNotNull(this.event);
		assertEquals(TableModelEvent.DELETE, this.event.getType());
		assertEquals(TableModelEvent.ALL_COLUMNS, this.event.getColumn());
	}

	public void testChangeCell() throws Exception {
		TableModelAdapter<Person> tableModelAdapter =  this.buildTableModelAdapter();
		this.event = null;
		tableModelAdapter.addTableModelListener(this.buildSingleEventListener());
		// add a person to the end of the list so we only trigger one event
		Person person = this.crowd.personNamed("Gollum");
		person.setEvil(true);
		assertNotNull(this.event);
		assertEquals(TableModelEvent.UPDATE, this.event.getType());
		assertEquals(PersonColumnAdapter.EVIL_COLUMN, this.event.getColumn());
	}

	public void testLazyListListener() throws Exception {
		TableModelAdapter<Person> tableModelAdapter =  this.buildTableModelAdapter();
		TableModelListener listener = this.buildTableModelListener();
		assertTrue(this.crowd.hasNoCollectionChangeListeners(Crowd.PEOPLE_COLLECTION));
		tableModelAdapter.addTableModelListener(listener);
		assertTrue(this.crowd.hasAnyCollectionChangeListeners(Crowd.PEOPLE_COLLECTION));
		tableModelAdapter.removeTableModelListener(listener);
		assertTrue(this.crowd.hasNoCollectionChangeListeners(Crowd.PEOPLE_COLLECTION));
	}

	public void testLazyCellListener() throws Exception {
		TableModelAdapter<Person> tableModelAdapter =  this.buildTableModelAdapter();
		TableModelListener listener = this.buildTableModelListener();
		Person person = this.crowd.personNamed("Gollum");
		assertTrue(person.hasNoPropertyChangeListeners(Person.NAME_PROPERTY));
		assertTrue(person.hasNoPropertyChangeListeners(Person.BIRTH_DATE_PROPERTY));
		assertTrue(person.hasNoPropertyChangeListeners(Person.EYE_COLOR_PROPERTY));
		assertTrue(person.hasNoPropertyChangeListeners(Person.EVIL_PROPERTY));
		assertTrue(person.hasNoPropertyChangeListeners(Person.RANK_PROPERTY));

		tableModelAdapter.addTableModelListener(listener);
		assertTrue(person.hasAnyPropertyChangeListeners(Person.NAME_PROPERTY));
		assertTrue(person.hasAnyPropertyChangeListeners(Person.BIRTH_DATE_PROPERTY));
		assertTrue(person.hasAnyPropertyChangeListeners(Person.EYE_COLOR_PROPERTY));
		assertTrue(person.hasAnyPropertyChangeListeners(Person.EVIL_PROPERTY));
		assertTrue(person.hasAnyPropertyChangeListeners(Person.RANK_PROPERTY));

		tableModelAdapter.removeTableModelListener(listener);
		assertTrue(person.hasNoPropertyChangeListeners(Person.NAME_PROPERTY));
		assertTrue(person.hasNoPropertyChangeListeners(Person.BIRTH_DATE_PROPERTY));
		assertTrue(person.hasNoPropertyChangeListeners(Person.EYE_COLOR_PROPERTY));
		assertTrue(person.hasNoPropertyChangeListeners(Person.EVIL_PROPERTY));
		assertTrue(person.hasNoPropertyChangeListeners(Person.RANK_PROPERTY));
	}

	private TableModelAdapter<Person> buildTableModelAdapter() {
		return new TableModelAdapter<Person>(this.buildSortedPeopleAdapter(), this.buildColumnAdapter()) {
			@Override
			protected PropertyChangeListener buildCellListener() {
				return this.buildCellListener_();
			}
			@Override
			protected ListChangeListener buildListChangeListener() {
				return this.buildListChangeListener_();
			}
		};
	}

	private ListValueModel<Person> buildSortedPeopleAdapter() {
		return new SortedListValueModelAdapter<Person>(this.buildPeopleAdapter());
	}

	private CollectionValueModel<Person> buildPeopleAdapter() {
		return new CollectionAspectAdapter<Crowd, Person>(Crowd.PEOPLE_COLLECTION, this.crowd) {
			@Override
			protected Iterator<Person> iterator_() {
				return this.subject.people();
			}
			@Override
			protected int size_() {
				return this.subject.peopleSize();
			}
		};
	}

	private Crowd buildCrowd() {
		Crowd result = new Crowd();
		result.addPerson("Bilbo");
		result.addPerson("Gollum");
		result.addPerson("Frodo");
		result.addPerson("Samwise");
		return result;
	}

	private TableModelAdapter.ColumnAdapter buildColumnAdapter() {
		return new PersonColumnAdapter();
	}

	private TableModelListener buildTableModelListener() {
		return new TestTableModelListener();
	}

	private List<String> sortedNames() {
		return new ArrayList<String>(CollectionTools.treeSet(this.crowd.peopleNames()));
	}

	private TableModelListener buildSingleEventListener() {
		return new TestTableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// we expect only a single event
				if (TableModelAdapterTests.this.event == null) {
					TableModelAdapterTests.this.event = e;
				} else {
					fail("unexpected event");
				}
			}
		};
	}


	// ********** classes **********

	public static class PersonColumnAdapter
		implements TableModelAdapter.ColumnAdapter
	{
		public static final int COLUMN_COUNT = 7;
	
		public static final int NAME_COLUMN = 0;
		public static final int BIRTH_DATE_COLUMN = 1;
		public static final int GONE_WEST_DATE_COLUMN = 2;
		public static final int EYE_COLOR_COLUMN = 3;
		public static final int EVIL_COLUMN = 4;
		public static final int RANK_COLUMN = 5;
		public static final int ADVENTURE_COUNT_COLUMN = 6;
	
		private static final String[] COLUMN_NAMES = new String[] {
			"Name",
			"Birth",
			"Gone West",
			"Eyes",
			"Evil",
			"Rank",
			"Adventures"
		};
	
	
		public int columnCount() {
			return COLUMN_COUNT;
		}
	
		public String columnName(int index) {
			return COLUMN_NAMES[index];
		}
	
		public Class<?> columnClass(int index) {
			switch (index) {
				case NAME_COLUMN:					return Object.class;
				case BIRTH_DATE_COLUMN:			return Date.class;
				case GONE_WEST_DATE_COLUMN:	return Date.class;
				case EYE_COLOR_COLUMN:			return Object.class;
				case EVIL_COLUMN:					return Boolean.class;
				case RANK_COLUMN:					return Integer.class;
				case ADVENTURE_COUNT_COLUMN:return Integer.class;
				default: 									return Object.class;
			}
		}
	
		public boolean columnIsEditable(int index) {
			return index != NAME_COLUMN;
		}
	
		public ModifiablePropertyValueModel<Object>[] cellModels(Object subject) {
			Person person = (Person) subject;
			@SuppressWarnings("unchecked")
			ModifiablePropertyValueModel<Object>[] result = new ModifiablePropertyValueModel[COLUMN_COUNT];
	
			result[NAME_COLUMN] = this.buildNameAdapter(person);
			result[BIRTH_DATE_COLUMN] = this.buildBirthDateAdapter(person);
			result[GONE_WEST_DATE_COLUMN] = this.buildGoneWestDateAdapter(person);
			result[EYE_COLOR_COLUMN] = this.buildEyeColorAdapter(person);
			result[EVIL_COLUMN] = this.buildEvilAdapter(person);
			result[RANK_COLUMN] = this.buildRankAdapter(person);
			result[ADVENTURE_COUNT_COLUMN] = this.buildAdventureCountAdapter(person);
	
			return result;
		}
	
		private ModifiablePropertyValueModel<Object> buildNameAdapter(Person person) {
			return new PropertyAspectAdapter<Person, Object>(Person.NAME_PROPERTY, person) {
				@Override
				protected String buildValue_() {
					return this.subject.getName();
				}
				@Override
				protected void setValue_(Object value) {
					this.subject.setName((String) value);
				}
			};
		}
	
		private ModifiablePropertyValueModel<Object> buildBirthDateAdapter(Person person) {
			return new PropertyAspectAdapter<Person, Object>(Person.BIRTH_DATE_PROPERTY, person) {
				@Override
				protected Date buildValue_() {
					return this.subject.getBirthDate();
				}
				@Override
				protected void setValue_(Object value) {
					this.subject.setBirthDate((Date) value);
				}
			};
		}
	
		private ModifiablePropertyValueModel<Object> buildGoneWestDateAdapter(Person person) {
			return new PropertyAspectAdapter<Person, Object>(Person.GONE_WEST_DATE_PROPERTY, person) {
				@Override
				protected Date buildValue_() {
					return this.subject.getGoneWestDate();
				}
				@Override
				protected void setValue_(Object value) {
					this.subject.setGoneWestDate((Date) value);
				}
			};
		}
	
		private ModifiablePropertyValueModel<Object> buildEyeColorAdapter(Person person) {
			return new PropertyAspectAdapter<Person, Object>(Person.EYE_COLOR_PROPERTY, person) {
				@Override
				protected String buildValue_() {
					return this.subject.getEyeColor();
				}
				@Override
				protected void setValue_(Object value) {
					this.subject.setEyeColor((String) value);
				}
			};
		}
	
		private ModifiablePropertyValueModel<Object> buildEvilAdapter(Person person) {
			return new PropertyAspectAdapter<Person, Object>(Person.EVIL_PROPERTY, person) {
				@Override
				protected Boolean buildValue_() {
					return Boolean.valueOf(this.subject.isEvil());
				}
				@Override
				protected void setValue_(Object value) {
					this.subject.setEvil(((Boolean)value).booleanValue());
				}
			};
		}
	
		private ModifiablePropertyValueModel<Object> buildRankAdapter(Person person) {
			return new PropertyAspectAdapter<Person, Object>(Person.RANK_PROPERTY, person) {
				@Override
				protected Integer buildValue_() {
					return new Integer(this.subject.getRank());
				}
				@Override
				protected void setValue_(Object value) {
					this.subject.setRank(((Integer) value).intValue());
				}
			};
		}
	
		private ModifiablePropertyValueModel<Object> buildAdventureCountAdapter(Person person) {
			return new PropertyAspectAdapter<Person, Object>(Person.ADVENTURE_COUNT_PROPERTY, person) {
				@Override
				protected Integer buildValue_() {
					return new Integer(this.subject.getAdventureCount());
				}
				@Override
				protected void setValue_(Object value) {
					this.subject.setAdventureCount(((Integer) value).intValue());
				}
			};
		}
	
	}


	public static class Crowd extends AbstractModel {
		private final Collection<Person> people;
			public static final String PEOPLE_COLLECTION = "people";
	
		public Crowd() {
			super();
			this.people = new ArrayList<Person>();
		}
	
	
		public Iterator<Person> people() {
			return IteratorTools.clone(this.people, new Closure<Person>() {
				public void execute(Person person) {
					Crowd.this.removePerson(person);
				}
			});
		}
	
		public int peopleSize() {
			return this.people.size();
		}
	
		public Person addPerson(String name) {
			this.checkPersonName(name);
			return this.addPerson(new Person(this, name));
		}
	
		private Person addPerson(Person person) {
			this.addItemToCollection(person, this.people, PEOPLE_COLLECTION);
			return person;
		}
	
		public void removePerson(Person person) {
			this.removeItemFromCollection(person, this.people, PEOPLE_COLLECTION);
		}
	
		public void removePeople(Collection<Person> persons) {
			this.removeItemsFromCollection(persons, this.people, PEOPLE_COLLECTION);
		}
	
		public void removePeople(Iterator<Person> persons) {
			this.removeItemsFromCollection(persons, this.people, PEOPLE_COLLECTION);
		}
	
		void checkPersonName(String personName) {
			if (personName == null) {
				throw new NullPointerException();
			}
			if (IteratorTools.contains(this.peopleNames(), personName)) {
				throw new IllegalArgumentException(personName);
			}
		}
	
		public Iterator<String> peopleNames() {
			return new TransformationIterator<Person, String>(this.people.iterator(), Person.NAME_TRANSFORMER);
		}
	
		public Person personNamed(String name) {
			for (Iterator<Person> stream = this.people.iterator(); stream.hasNext(); ) {
				Person person = stream.next();
				if (person.getName().equals(name)) {
					return person;
				}
			}
			return null;
		}
	
		@Override
		public String toString() {
			return ObjectTools.toString(this, String.valueOf(this.people.size()) + " people");
		}
	
	}
	
	
	public static class Person extends AbstractModel implements Comparable<Person> {
		private Crowd crowd;
		private String name;
			public static final String NAME_PROPERTY= "name";
		private Date birthDate;
			public static final String BIRTH_DATE_PROPERTY= "birthDate";
		private Date goneWestDate;
			public static final String GONE_WEST_DATE_PROPERTY= "goneWestDate";
		private String eyeColor;
			public static final String EYE_COLOR_PROPERTY= "eyeColor";
			public static final String EYE_COLOR_BLUE = "blue";
			public static final String EYE_COLOR_GREEN = "green";
			public static final String EYE_COLOR_BROWN = "brown";
			public static final String EYE_COLOR_HAZEL = "hazel";
			public static final String EYE_COLOR_PINK = "pink";
			private static Collection<String> validEyeColors;
			public static final String DEFAULT_EYE_COLOR = EYE_COLOR_BLUE;
		private boolean evil;
			public static final String EVIL_PROPERTY= "evil";
		private int rank;
			public static final String RANK_PROPERTY= "rank";
		private int adventureCount;
			public static final String ADVENTURE_COUNT_PROPERTY= "adventureCount";
	
		Person(Crowd crowd, String name) {
			super();
			this.crowd = crowd;
			this.name = name;
			this.birthDate = new Date();
			Calendar c = Calendar.getInstance();
			c.add(Calendar.YEAR, 250);
			this.goneWestDate = new Date(c.getTimeInMillis());
			this.eyeColor = DEFAULT_EYE_COLOR;
			this.evil = false;
			this.rank = 0;
			this.adventureCount = 0;
		}
	
		public static Collection<String> getValidEyeColors() {
			if (validEyeColors == null) {
				validEyeColors = buildValidEyeColors();
			}
			return validEyeColors;
		}
	
		private static Collection<String> buildValidEyeColors() {
			Collection<String> result = new ArrayList<String>();
			result.add(EYE_COLOR_BLUE);
			result.add(EYE_COLOR_GREEN);
			result.add(EYE_COLOR_BROWN);
			result.add(EYE_COLOR_HAZEL);
			result.add(EYE_COLOR_PINK);
			return result;
		}
	
		public Crowd getCrowd() {
			return this.crowd;
		}
	
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			this.crowd.checkPersonName(name);
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}
		public static final Transformer<Person, String> NAME_TRANSFORMER = new NameTransformer();
		public static class NameTransformer
			extends TransformerAdapter<Person, String>
		{
			@Override
			public String transform(Person person) {
				return person.getName();
			}
		}
	
		public Date getBirthDate() {
			return this.birthDate;
		}
		public void setBirthDate(Date birthDate) {
			Object old = this.birthDate;
			this.birthDate = birthDate;
			this.firePropertyChanged(BIRTH_DATE_PROPERTY, old, birthDate);
		}
	
		public Date getGoneWestDate() {
			return this.goneWestDate;
		}
		public void setGoneWestDate(Date goneWestDate) {
			Object old = this.goneWestDate;
			this.goneWestDate = goneWestDate;
			this.firePropertyChanged(GONE_WEST_DATE_PROPERTY, old, goneWestDate);
		}
	
		public String getEyeColor() {
			return this.eyeColor;
		}
		public void setEyeColor(String eyeColor) {
			if (! getValidEyeColors().contains(eyeColor)) {
				throw new IllegalArgumentException(eyeColor);
			}
			Object old = this.eyeColor;
			this.eyeColor = eyeColor;
			this.firePropertyChanged(EYE_COLOR_PROPERTY, old, eyeColor);
		}
	
		public boolean isEvil() {
			return this.evil;
		}
		public void setEvil(boolean evil) {
			boolean old = this.evil;
			this.evil = evil;
			this.firePropertyChanged(EVIL_PROPERTY, old, evil);
		}
	
		public int getRank() {
			return this.rank;
		}
		public void setRank(int rank) {
			int old = this.rank;
			this.rank = rank;
			this.firePropertyChanged(RANK_PROPERTY, old, rank);
		}
	
		public int getAdventureCount() {
			return this.adventureCount;
		}
		public void setAdventureCount(int adventureCount) {
			int old = this.adventureCount;
			this.adventureCount = adventureCount;
			this.firePropertyChanged(ADVENTURE_COUNT_PROPERTY, old, adventureCount);
		}
	
		public int compareTo(Person p) {
			return this.name.compareToIgnoreCase(p.name);
		}
	
		@Override
		public String toString() {
			return this.name + 
						"\tborn: " + DateFormat.getDateInstance().format(this.birthDate) + 
						"\tgone west: " + DateFormat.getDateInstance().format(this.goneWestDate) + 
						"\teyes: " + this.eyeColor + 
						"\tevil: " + this.evil + 
						"\trank: " + this.rank +
						"\tadventures: " + this.adventureCount
			;
		}
	
	}


	private class TestTableModelListener implements TableModelListener {
		TestTableModelListener() {
			super();
		}
		public void tableChanged(TableModelEvent e) {
			fail("unexpected event");
		}
	}

}
