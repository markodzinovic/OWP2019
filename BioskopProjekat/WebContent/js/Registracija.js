$(document).ready(function() {
	var korisnickoImeI = $('#korisnickoImeI');
	var lozinkaI = $('#lozinkaI');
	var ponovljenaLozinkaI = $('#ponovljenaLozinkaI');

	var messageParagraph = $('#messageParagraph');

	$('#potvrdaRegistracije').on('click', function(event) {
		var korisnickoIme = korisnickoImeI.val();
		var lozinka = lozinkaI.val();
		var ponovljenaLozinka = ponovljenaLozinkaI.val();
		console.log('korisnickoIme: ' + korisnickoIme);
		console.log('lozinka: ' + lozinka);
		console.log('ponovljenaLozinka: ' + ponovljenaLozinka);

		if (lozinka != ponovljenaLozinka) {
			messageParagraph.text('Lozinke se ne podudaraju!');

			event.preventDefault();
			return false;
		}
		
		var params = {
			'korisnickoIme': korisnickoIme, 
			'lozinka': lozinka
		}
		$.post('RegistracijaServlet', params, function(data) {
			console.log(data);

			if (data.status == 'failure') {
				messageParagraph.text(data.message);
				return;
			}
			if (data.status == 'success') {	
				window.location.replace('Login.html');
				return;
			}
		});

		event.preventDefault();
		return false;
	});
});