$('#file_form').submit(
	function(e) {
		
		e.preventDefault();

		var form = new FormData(this);

		let name = document.getElementById('name').value;
		let object = {
			name: name
		}

		form.append(
			'fileInfo',
			new Blob(
				[JSON.stringify(object)],
				{ type: "application/json" }
			)
		);

		$.ajax({
			method: 'POST',
			url: './saveFiles',
			data: form,
			cash: false,
			processData: false,
			contentType: false,
			success: function(data) {
				alert(data);
			}
		});

	});