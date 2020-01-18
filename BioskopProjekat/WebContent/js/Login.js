$(document).ready(function() { // izvršava se nakon što se izgradi DOM stablo HTML dokumenta
	// keširanje referenci da se ne bi ponavljale pretrage kroz DOM stablo
	var korisnickoImeI = $('#korisnickoIme');
	var lozinkaI = $('#lozinka');

	$('#prijava').on('click', function(event) { // izvršava se na klik na dugme
		var korisnickoIme = korisnickoImeI.val();
		var lozinka = lozinkaI.val();
		console.log('korisnickoIme: ' + korisnickoIme);
		console.log('lozinka: ' + lozinka);		

		var params = {
			'korisnickoIme': korisnickoIme, 
			'lozinka': lozinka
		}
		// kontrola toka se račva na 2 grane
		$.post('LoginServlet', params, function(data) { // u posebnoj programskoj niti se šalje (asinhroni) zahtev
			// tek kada stigne odgovor izvršiće se ova anonimna funkcija
			console.log('stigao odgovor!')
			console.log(data);

			if (data.status == 'failure') {
				korisnickoImeI.val('');
				lozinkaI.val('');

				return;
			}
			if (data.status == 'success') {
				alert("Logovan");
			}
		});
		// program se odmah nastavlja dalje, pre nego što stigne odgovor
		console.log('poslat zahtev!')

		// zabraniti da browser obavi podrazumevanu akciju pri događaju
		event.preventDefault();
		return false;
	});
});