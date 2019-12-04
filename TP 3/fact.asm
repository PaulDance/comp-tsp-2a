
.text
		addiu	$a0, $zero, 10
		jal		fact
		
		move	$a0, $v0
		li		$v0, 1
		syscall

	exit:
		li		$v0, 10				# exit program
		syscall


	fact:
		subiu	$sp, $sp, 8			# reserve 8 bytes on the stack
		sw		$ra, ($sp)			# save $ra in $sp
		sw		$t0, 4($sp)
	# if $a0 == 0:
		bnez	$a0, else
	# then:
		li		$v0, 1
		j		factRet
	else:
		move	$t0, $a0
		subiu	$a0, $a0, 1
		jal		fact
		
		multu	$t0, $v0
		mflo	$v0
		# j		factRet
		
	factRet:
		lw		$ra, ($sp)			# restore saved values
		lw		$t0, 4($sp)
		addiu	$sp, $sp, 8			# "free" the 8 bytes.
		jr		$ra
