/**
 * 
 */
package com.callil.rotatingsentries.entityComponentSystem.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.SparseArray;

import com.callil.rotatingsentries.entityComponentSystem.components.Component;

/**
 * @author Callil
 *
 */
public class EntityManager {
	
	/**
	 * The list of entity ids currently in the game.
	 */
	private List<Integer> entities;
	
	/**
	 * Map that gives, for a given component class name the map registering
	 * for each entity id the component of this class it holds.
	 */
	private Map<String, SparseArray<Component>> componentsByClass;

	/**
	 * The lowest eid that is yet to be assigned.
	 */
	private int lowestUnassignedEid;
	
	/**
	 * Constructor.
	 */
	public EntityManager() {
		this.entities = new ArrayList<Integer>();
		this.componentsByClass = new HashMap<String, SparseArray<Component>>();
		this.lowestUnassignedEid = 0;
	}
	
	
	//Methods
	
	/**
	 * Generate a new unique eid.
	 * @return the eid generated.
	 */
	public int generateNewEid() {
		return lowestUnassignedEid++;
	}

	/**
	 * Create a new entity and add it to the currently active entities list.
	 * @return the entity created.
	 */
	public Entity createEntity() {
		int eid = generateNewEid();
		entities.add(eid);
		return new Entity(eid);
	}
	
	/**
	 * Add a component to an entity.
	 * @param component the component to add.
	 * @param entity the entity to which the component will be added.
	 */
	public void addComponentToEntity(Component component, Entity entity) {
		SparseArray<Component> components = this.getComponentsByClass().get(component.getClass().getName());
		if (components == null) {
			components = new SparseArray<Component>();
			this.getComponentsByClass().put(component.getClass().getName(), components);
		}
		components.put(entity.getEid(), component);
	}
	
	/**
	 * Remove a component from an entity.
	 * @param componentClassName the class of the component.
	 * @param entity the entity
	 */
	public void removeComponentFromEntity(String componentClassName, Entity entity) {
		SparseArray<Component> components = this.getComponentsByClass().get(componentClassName);
		if (components != null) {
			components.remove(entity.getEid());
		}
	}
	
	/**
	 * For a given entity, retrieve the component of the given class.
	 * @param componentClassName the name of the class of the component (getClass().getName()).
	 * @param entity the entity we want to retrieve the component from.
	 * @return The component linked to this entity, null if there isn't any.
	 */
	public Component getComponent(String componentClassName, Entity entity) {
		SparseArray<Component> map = this.getComponentsByClass().get(componentClassName);
		if (map != null) {
			return this.getComponentsByClass().get(componentClassName).get(entity.getEid());
		}
		return null;
	}
	
	/**
	 * Remove an entity.
	 * @param entity the entity to remove
	 */
	public void removeEntity(Entity entity) {
		Collection<SparseArray<Component>> listeMapEntity = this.getComponentsByClass().values();
		for (SparseArray<Component> mapEntity : listeMapEntity) {
			//For each component class, remove the entity's id from the map if it exists
			Component componentDelete = mapEntity.get(entity.getEid());
			if (componentDelete != null) {
				mapEntity.remove(entity.getEid());
				componentDelete.destroy();
			}
		}
		entities.remove(Integer.valueOf(entity.getEid()));
	}
	
	/**
	 * For a given component class name, retrieve all entities that have a component of this class.
	 * @param componentClassName the component class name (getClass().getName()).
	 * @return A list of entities that have a component of this class. An empty list if no entity has this type
	 * of component.
	 */
	public List<Entity> getAllEntitiesPosessingComponentOfClass(String componentClassName) {
		List<Entity> results = new ArrayList<Entity>();
		SparseArray<Component> components = this.getComponentsByClass().get(componentClassName);
		for(int i = 0; i < components.size(); i++) {
		   results.add(new Entity(components.keyAt(i)));
		}
		return results;
	}

	
	
	//Getters & Setters
	
	/**
	 * @return the entities
	 */
	public List<Integer> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(List<Integer> entities) {
		this.entities = entities;
	}

	/**
	 * @return the componentsByClass
	 */
	public Map<String, SparseArray<Component>> getComponentsByClass() {
		return componentsByClass;
	}

	/**
	 * @param componentsByClass the componentsByClass to set
	 */
	public void setComponentsByClass(Map<String, SparseArray<Component>> componentsByClass) {
		this.componentsByClass = componentsByClass;
	}

	/**
	 * @return the lowestUnassignedEid
	 */
	public int getLowestUnassignedEid() {
		return lowestUnassignedEid;
	}

	/**
	 * @param lowestUnassignedEid the lowestUnassignedEid to set
	 */
	public void setLowestUnassignedEid(int lowestUnassignedEid) {
		this.lowestUnassignedEid = lowestUnassignedEid;
	}

}
