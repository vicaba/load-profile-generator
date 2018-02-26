package domain.in.config;

import domain.in.distribution.InputDistribution;
import domain.in.field.InputField;
import domain.in.field.options.Options;
import domain.in.rule.InputRule;

import java.util.ArrayList;

/**
 * Class used to obtain and keep all the information regarding the input configurations.
 *
 * @version 1.0
 * @author Albert Trias
 * @since 27/11/2017
 */
public class InputConfiguration {
  /** ArrayList with the list of fields obtained from the config file. */
  private ArrayList<InputField<Options>> fields;
  /** ArrayList with the list of stream.rules obtained from the config file. */
  private ArrayList<InputRule> rules;
  /** ArrayList with the list of distributions from the config file. */
  private ArrayList<InputDistribution> distributions;

  /** Constructor. */
  public InputConfiguration() {
    this.fields = new ArrayList<>();
    this.rules = new ArrayList<>();
    this.distributions = new ArrayList<>();
  }

  /**
   * Getter for the list of fields value.
   *
   * @return ArrayList with the list of fields.
   */
  public ArrayList<InputField<Options>> getFields() {
    return this.fields;
  }

  /**
   * Getter for the list of results value.
   *
   * @return ArrayList with the list of results.
   */
  public ArrayList<InputRule> getRules() {
    return this.rules;
  }

  /**
   * Getter of one specific field inside the list of fields.
   *
   * @param field Integer that indicates which field we get, goes from 0 to length of fields.
   * @return Field that was chosen with the field parameter.
   */
  public InputField getField(int field) {
    return this.fields.get(field);
  }

  /**
   * Getter of one specific field inside the list of stream.rules.
   *
   * @param rule Integer that indicates which field we get, goes from 0 to length of stream.rules.
   * @return Field that was chosen with the rule parameter.
   */
  public InputRule getRule(int rule) {
    return this.rules.get(rule);
  }

  /**
   * Getter for the list of distributions.
   *
   * @return ArrayList with the list of distributions.
   */
  public ArrayList<InputDistribution> getDistributions() {
    return this.distributions;
  }

  /**
   * Function that indicates if a specific id has to be connected to a Broadcast Node in the Graph.
   *
   * @param id String with the id of the node that is being checked.
   * @return Returns true if it has to be connected, and false if it doesn't need to be connected.
   */
  public boolean isBroadcast(String id) {
    for (InputDistribution dist : distributions) {
      if (dist.getId().equals(id)) return true;
    }
    return false;
  }

  /**
   * Function that indicates if a specific id will be distributed by other nodes.
   *
   * @param id String with the id of the node that is being checked.
   * @return Returns true if it has to be distributed, and false if it doesn't.
   */
  public boolean isDistribution(String id) {
    for (InputDistribution dist : distributions) {
      if (dist.isDistribution(id)) return true;
    }
    return false;
  }

  /**
   * Function that returns the stream.distribution stream.rules of the nodes that distribute the node with the id
   * passed as parameter.
   *
   * @param idDist String with the id of the field that we want to check who distributes it.
   * @return An ArrayList of the InputDistribution that deal with the passed id as parameter.
   */
  public ArrayList<InputDistribution> isDistributedBy(String idDist) {
    ArrayList<InputDistribution> listIDs = new ArrayList<>();
    for (InputDistribution distribution : distributions) {
      if (distribution.getResult().getId().equals(idDist)) {
        listIDs.add(distribution);
      }
    }
    return listIDs;
  }
}
