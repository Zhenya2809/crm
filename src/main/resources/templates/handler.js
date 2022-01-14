function check(form) {
  var firstname = form.firstname.value;
  var pass = form.pass.value;
  var message = form.message.value;
  var rules = form.rules.value;
  var file = form.fileupload.value;
  var bad = "";
  if (firstname.length < 3)
     bad += "Имя слишком короткое" + "\n";
  if (firstname.length > 32)
    bad += "Имя слишком длинное" + "\n";
  if (pass.length < 3)
    bad += "Пароль слишком короткий" + "\n";
  if (pass.length > 32)
    bad += "Пароль слишком длинный" + "\n";
  if (message.length < 3)
    bad += "Сообщение слишком короткое" + "\n";
  if (rules != "on")
    bad += "Вы не согласились с правилами" + "\n";
  if (file.length == 0)
    bad += "Вы не выбрали файл для загрузки" + "\n";
  if (bad != "") {
    bad = "Неверно заполнена форма:" + "\n" + bad;
    alert(bad);
    return false;
  }
  return true;
}