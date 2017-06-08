var HOME = "localhost:4567/";

// set up for index.js
$(document).ready(function() {
  // --- add onclick callbacks ---
  // button, classify
  $('#button-classify').click(function() {
    // - get creatuer data -
    // defense
    var creature_hp = $('#hp').val();
    var creature_hd = $('#hd').val();
    var creature_ac = $('#ac').val();
    var creature_touch = $('#touch').val();
    var creature_flat_footed = $('#flat-footed').val();
    var creature_fort = $('#fort').val();
    var creature_ref = $('#ref').val();
    var creature_will = $('#will').val();
    // statistics
    var creature_str = $('#strength').val();
    var creature_dex = $('#dexterity').val();
    var creature_con = $('#constitution').val();
    var creature_int = $('#intelligence').val();
    var creature_wis = $('#wisdom').val();
    var creature_cha = $('#charisma').val();
    // offense
    var creature_bab = $('#bab').val();
    var creature_cmb = $('#cmb').val();
    var creature_cmd = $('#cmd').val();

    // redirect to classification page
    var creature = [
      creature_hp, creature_hd,
      creature_ac, creature_touch, creature_flat_footed,
      creature_fort, creature_ref, creature_will,
      creature_str, creature_dex, creature_con,
      creature_int, creature_wis, creature_cha,
      creature_bab, creature_cmb, creature_cmd
    ].join(",");
    window.location.href = "classify/" + creature
  });

  // button, load
  // TODO: implement the load button
  /*$('#button-load').click(function() {
    console.log('load!');
  });*/

  // button, save
  // TODO: implement a save button
});
