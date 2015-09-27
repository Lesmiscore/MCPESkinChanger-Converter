package com.nao20010128nao.MCPE.SC;

public enum Formats {
	JPEG{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return data[0]==0xff&data[1]==0xd8;
		}
	},
	PNG{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return 
				data[0]==0x89&
				data[1]=='P'&
				data[2]=='N'&
				data[3]=='G'&
				data[4]==0x0d&
				data[5]==0x0a&
				data[6]==0x1a&
				data[7]==0x0a;
		}
	},
	GIF{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return 
				data[0]=='G'&
				data[1]=='I'&
				data[2]=='F'&
				data[3]=='8'&
				(data[4]=='7'|data[4]=='9')&
				data[5]=='a';
		}		
	},
	WEBP{
		@Override
		public boolean isCorrectFormat(byte[] data)
		{
			// TODO: Implement this method
			return 
				data[0]=='W'&
				data[1]=='E'&
				data[2]=='B'&
				data[3]=='P';
		}
	};
	public abstract boolean isCorrectFormat(byte[] data);
}
